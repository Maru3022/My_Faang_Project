package com.devpath

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.put
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor
import org.testcontainers.containers.PostgreSQLContainer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest(properties = [
    "spring.security.oauth2.resourceserver.jwt.issuer-uri=https://identity.example.test",
    "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://identity.example.test/jwks",
    "springdoc.api-docs.enabled=true"
])
@AutoConfigureMockMvc
@Import(FoundationIntegrationTest.DatabaseConfiguration::class)
class FoundationIntegrationTest {
    @TestConfiguration(proxyBeanMethods = false)
    class DatabaseConfiguration {
        @Bean @ServiceConnection
        fun postgres() = PostgreSQLContainer("postgres:17-alpine")
    }
    @Autowired lateinit var mvc: MockMvc
    @Autowired lateinit var jdbc: JdbcTemplate

    @BeforeEach
    fun resetUserData() {
        // Tests share one Postgres container; give each test a clean slate.
        jdbc.update("DELETE FROM event_publication")
        jdbc.update("DELETE FROM legal.user_acceptance")
        jdbc.update("DELETE FROM identity.identity_mapping")
        jdbc.update("DELETE FROM profile.user_profile")
    }

    @Test fun `migrations create profile and durable event tables`() {
        assertThat(jdbc.queryForObject("SELECT count(*) FROM profile.user_profile", Long::class.java)).isZero()
        assertThat(jdbc.queryForObject("SELECT count(*) FROM event_publication", Long::class.java)).isZero()
    }
    @Test fun `health is public and database is ready`() {
        mvc.get("/actuator/health").andExpect { status { isOk() }; jsonPath("$.status") { value("UP") } }
    }
    @Test fun `anonymous API request has standard problem and request ID`() {
        mvc.get("/api/v1/platform/status") { header("X-Request-ID", "test-request") }.andExpect {
            status { isUnauthorized() }; content { contentType("application/problem+json") }
            jsonPath("$.status") { value(401) }; jsonPath("$.requestId") { value("test-request") }
            header { string("X-Request-ID", "test-request") }
        }
    }
    @Test fun `authenticated API request succeeds without session`() {
        mvc.get("/api/v1/platform/status") { with(jwt()) }.andExpect {
            status { isOk() }; jsonPath("$.status") { value("ready") }; cookie { doesNotExist("JSESSIONID") }
        }
    }
    @Test fun `malformed bearer token is rejected`() {
        mvc.get("/api/v1/platform/status") { header("Authorization", "Bearer invalid") }.andExpect { status { isUnauthorized() } }
    }
    @Test fun `unsafe request ID is replaced`() {
        val result = mvc.get("/actuator/health") { header("X-Request-ID", "unsafe value") }.andReturn()
        assertThat(result.response.getHeader("X-Request-ID")).matches("[a-f0-9-]{36}")
    }
    @Test fun `OpenAPI describes authenticated endpoint`() {
        mvc.get("/v3/api-docs") { with(jwt()) }.andExpect {
            status { isOk() }; jsonPath("$.paths['/api/v1/platform/status']") { exists() }
        }
    }

    @Test fun `first me request auto-registers identity and default profile`() {
        mvc.get("/api/v1/me") { with(userToken("alice")) }.andExpect {
            status { isOk() }
            jsonPath("$.userId") { exists() }
            jsonPath("$.locale") { value("en") }
            jsonPath("$.timezone") { value("UTC") }
            jsonPath("$.dailyMinutes") { value(null) }
        }
        assertThat(jdbc.queryForObject("SELECT count(*) FROM identity.identity_mapping", Long::class.java)).isEqualTo(1L)
        assertThat(jdbc.queryForObject("SELECT count(*) FROM profile.user_profile", Long::class.java)).isEqualTo(1L)
    }

    @Test fun `same external identity resolves to the same user`() {
        mvc.get("/api/v1/me") { with(userToken("bob")) }.andExpect { status { isOk() } }
        mvc.get("/api/v1/me") { with(userToken("bob")) }.andExpect { status { isOk() } }
        assertThat(jdbc.queryForObject("SELECT count(*) FROM identity.identity_mapping", Long::class.java)).isEqualTo(1L)
    }

    @Test fun `distinct external subjects are distinct users`() {
        mvc.get("/api/v1/me") { with(userToken("carol")) }.andExpect { status { isOk() } }
        mvc.get("/api/v1/me") { with(userToken("dave")) }.andExpect { status { isOk() } }
        assertThat(jdbc.queryForObject("SELECT count(*) FROM identity.identity_mapping", Long::class.java)).isEqualTo(2L)
    }

    @Test fun `profile update persists and rejects invalid input`() {
        mvc.patch("/api/v1/me") {
            with(userToken("erin"))
            contentType = MediaType.APPLICATION_JSON
            content = """{"displayName":"Erin","timezone":"Europe/Vilnius","locale":"en-US","dailyMinutes":60}"""
        }.andExpect {
            status { isOk() }
            jsonPath("$.displayName") { value("Erin") }
            jsonPath("$.timezone") { value("Europe/Vilnius") }
            jsonPath("$.locale") { value("en-US") }
            jsonPath("$.dailyMinutes") { value(60) }
            jsonPath("$.version") { value(1) }
        }
        assertThat(jdbc.queryForObject("SELECT daily_minutes FROM profile.user_profile", Integer::class.java)).isEqualTo(60)

        mvc.patch("/api/v1/me") {
            with(userToken("erin"))
            contentType = MediaType.APPLICATION_JSON
            content = """{"timezone":"Not/AZone"}"""
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("invalid_timezone") }
        }
    }

    @Test fun `legal acceptance records current version and rejects outdated one`() {
        mvc.get("/api/v1/legal/acceptances") { with(userToken("frank")) }.andExpect {
            status { isOk() }
            jsonPath("$.current.PRIVACY_POLICY") { value(1) }
            jsonPath("$.current.TERMS_OF_SERVICE") { value(1) }
            jsonPath("$.accepted.PRIVACY_POLICY") { value(0) }
        }
        mvc.put("/api/v1/legal/acceptances/PRIVACY_POLICY") {
            with(userToken("frank"))
            contentType = MediaType.APPLICATION_JSON
            content = """{"version":1}"""
        }.andExpect { status { isOk() } }
        mvc.get("/api/v1/legal/acceptances") { with(userToken("frank")) }.andExpect {
            status { isOk() }
            jsonPath("$.accepted.PRIVACY_POLICY") { value(1) }
        }
        mvc.put("/api/v1/legal/acceptances/TERMS_OF_SERVICE") {
            with(userToken("frank"))
            contentType = MediaType.APPLICATION_JSON
            content = """{"version":99}"""
        }.andExpect {
            status { isConflict() }
            jsonPath("$.code") { value("legal_version_outdated") }
        }
    }

    private companion object {
        val identityUrl = "https://identity.example.test"

        fun userToken(subject: String): JwtRequestPostProcessor =
            jwt().jwt { it.issuer(identityUrl).subject(subject) }
    }
}
