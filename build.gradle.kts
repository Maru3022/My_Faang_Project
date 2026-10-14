plugins {
    kotlin("jvm") version "2.1.21" apply false
    kotlin("plugin.spring") version "2.1.21" apply false
    id("org.springframework.boot") version "3.5.16" apply false
}
allprojects {
    group = "com.devpath"
    version = "0.1.0-SNAPSHOT"
    repositories { mavenCentral() }
}
subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
        jvmToolchain(21)
        compilerOptions { freeCompilerArgs.add("-Xjsr305=strict") }
    }
    tasks.withType<Test>().configureEach { useJUnitPlatform() }
}
