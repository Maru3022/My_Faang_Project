package com.devpath.platform

import com.devpath.platform.dto.MeResponse
import com.devpath.platform.dto.UpdateMeRequest
import com.devpath.userprofile.UserProfileFacade
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MeController(
    private val currentUser: CurrentUser,
    private val profileFacade: UserProfileFacade,
) {
    @GetMapping("/api/v1/me")
    @Operation(summary = "Get the authenticated user's profile", security = [SecurityRequirement(name = "bearerAuth")])
    fun me(@AuthenticationPrincipal jwt: Jwt): MeResponse =
        MeResponse.from(profileFacade.ensureProfile(currentUser.requireId(jwt)))

    @PatchMapping("/api/v1/me")
    @Operation(summary = "Update the authenticated user's profile", security = [SecurityRequirement(name = "bearerAuth")])
    fun update(@AuthenticationPrincipal jwt: Jwt, @RequestBody body: UpdateMeRequest): MeResponse {
        val userId = currentUser.requireId(jwt)
        return MeResponse.from(profileFacade.update(userId, body.toCommand()))
    }
}