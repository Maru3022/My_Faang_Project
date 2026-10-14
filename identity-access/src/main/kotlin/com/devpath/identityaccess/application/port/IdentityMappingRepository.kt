package com.devpath.identityaccess.application.port

import java.util.UUID

/**
 * Persistence port for the external identity -> internal user mapping.
 * Implemented by the JDBC adapter; swapped out in tests or when the
 * persistence technology changes.
 */
interface IdentityMappingRepository {
    fun findByProviderAndSubject(provider: String, subject: String): UUID?
    fun insert(provider: String, subject: String, userId: UUID)
}