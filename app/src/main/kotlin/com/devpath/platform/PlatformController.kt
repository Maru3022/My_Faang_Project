package com.devpath.platform

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
class PlatformController {
    @GetMapping("/api/v1/platform/status")
    @Operation(summary = "Authenticated foundation smoke check", security = [SecurityRequirement(name = "bearerAuth")])
    fun status() = mapOf("status" to "ready")
}
