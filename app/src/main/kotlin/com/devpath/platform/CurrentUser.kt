package com.devpath.platform

import com.devpath.identityaccess.IdentityAccessFacade
import com.devpath.sharedkernel.DomainErrorStatus
import com.devpath.sharedkernel.DomainException
import com.devpath.sharedkernel.UserId
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

/**
 * Resolves the authenticated JWT to the internal [UserId], creating the
 * identity mapping on first sight. The identity is always taken from the
 * verified security context, never from a request body.
 */
@Component
class CurrentUser(private val identityAccess: IdentityAccessFacade) {

    fun requireId(jwt: Jwt): UserId {
        val provider = jwt.getClaimAsString("iss")
            ?: throw DomainException(DomainErrorStatus.BAD_REQUEST, "missing_issuer", "token has no issuer claim")
        val subject = jwt.getClaimAsString("sub")
            ?: throw DomainException(DomainErrorStatus.BAD_REQUEST, "missing_subject", "token has no subject claim")
        return identityAccess.resolveUser(provider, subject).userId
    }
}