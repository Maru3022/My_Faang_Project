plugins {
    kotlin("jvm")
    id("org.springframework.boot")
}
dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.5.16"))
    implementation(platform("org.springframework.modulith:spring-modulith-bom:1.4.13"))
    // Security overrides until the Spring Boot BOM includes these patched releases.
    constraints {
        implementation("com.fasterxml.jackson.core:jackson-core:2.21.7")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.21.7")
    }
    implementation(project(":shared-kernel"))
    implementation(project(":user-profile"))
    implementation(project(":identity-access"))
    implementation(project(":legal"))
    // The managed Tomcat release has critical advisories whose fixed build is not yet on Maven Central.
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.modulith:spring-modulith-starter-jdbc")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.17")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation(kotlin("reflect"))
    runtimeOnly("org.postgresql:postgresql:42.7.12")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
tasks.bootJar { archiveFileName.set("devpath-ai.jar") }
tasks.test { exclude("**/*IntegrationTest.class") }
val integrationTest by tasks.registering(Test::class) {
    description = "Runs PostgreSQL integration tests (requires Docker)"
    group = "verification"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    include("**/*IntegrationTest.class")
    shouldRunAfter(tasks.test)
}
tasks.check { dependsOn(integrationTest) }
