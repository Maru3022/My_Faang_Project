package com.devpath.platform

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ProblemDetail
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Configuration
class SecurityConfiguration(private val mapper: ObjectMapper) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = http
        // Only Authorization bearer tokens are accepted; no cookie authentication.
        .csrf { it.disable() }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .authorizeHttpRequests {
            it.requestMatchers("/actuator/health", "/actuator/health/liveness", "/actuator/health/readiness").permitAll()
                .anyRequest().authenticated()
        }
        .oauth2ResourceServer { it.jwt {}.authenticationEntryPoint { req, res, _ -> unauthorized(req, res) } }
        .exceptionHandling {
            it.authenticationEntryPoint { req, res, _ -> unauthorized(req, res) }
            it.accessDeniedHandler { req, res, _ -> problem(req, res, HttpStatus.FORBIDDEN) }
        }
        .build()

    private fun unauthorized(req: HttpServletRequest, res: HttpServletResponse) {
        res.setHeader("WWW-Authenticate", "Bearer")
        problem(req, res, HttpStatus.UNAUTHORIZED)
    }
    private fun problem(req: HttpServletRequest, res: HttpServletResponse, status: HttpStatus) {
        res.status = status.value()
        res.contentType = "application/problem+json"
        val body = ProblemDetail.forStatus(status)
        body.setProperty("requestId", req.getAttribute("requestId"))
        mapper.writeValue(res.outputStream, body)
    }
}
