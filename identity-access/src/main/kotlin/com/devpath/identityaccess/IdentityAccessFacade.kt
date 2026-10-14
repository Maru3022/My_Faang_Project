package com.devpath.identityaccess

import com.devpath.sharedkernel.UserId

/**
 * Public contract of the identity-access module. Maps an externally-verified
 * OIDC identity (issuer + subject) to the stable internal [UserId] used across
 * the rest of the product.
 */
interface IdentityAccessFacade {
    /**
     * Returns the internal user for the given external identity, creating both
     * the identity mapping and the internal user on first sight. Idempotent: the
     * same provider/subject always resolves to the same [UserId].
     */
    fun resolveUser(provider: String, subject: String): ResolvedUser
}

data class ResolvedUser(
    val userId: UserId,
    val newlyRegistered: Boolean,
)