package com.devpath.platform

import com.devpath.legal.LegalDocumentType
import com.devpath.legal.LegalFacade
import com.devpath.platform.dto.LegalAcceptRequest
import com.devpath.platform.dto.LegalAcceptanceResponse
import com.devpath.sharedkernel.DomainErrorStatus
import com.devpath.sharedkernel.DomainException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LegalController(
    private val currentUser: CurrentUser,
    private val legalFacade: LegalFacade,
) {
    @GetMapping("/api/v1/legal/acceptances")
    @Operation(summary = "Current and accepted legal document versions", security = [SecurityRequirement(name = "bearerAuth")])
    fun acceptances(@AuthenticationPrincipal jwt: Jwt): LegalAcceptanceResponse {
        val userId = currentUser.requireId(jwt)
        return LegalAcceptanceResponse(
            current = legalFacade.currentVersions(),
            accepted = legalFacade.acceptedVersions(userId),
        )
    }

    @PutMapping("/api/v1/legal/acceptances/{type}")
    @Operation(summary = "Record acceptance of a legal document version", security = [SecurityRequirement(name = "bearerAuth")])
    fun accept(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable type: String,
        @RequestBody body: LegalAcceptRequest,
    ) {
        val documentType = LegalDocumentType.entries.firstOrNull { it.name == type.uppercase() }
            ?: throw DomainException(DomainErrorStatus.NOT_FOUND, "legal_document_unknown", "unknown legal document: $type")
        legalFacade.accept(currentUser.requireId(jwt), documentType, body.version)
    }
}