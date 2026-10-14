package com.devpath

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.modulith.core.ApplicationModules

class ArchitectureTest {
    @Test
    fun `module boundaries contain no cycles or internal access`() {
        val modules = ApplicationModules.of(DevPathApplication::class.java)
        assertThat(modules.map { it.identifier.toString() }).contains(
            "userprofile", "identityaccess", "legal", "sharedkernel", "platform",
        )
        modules.verify()
    }
}
