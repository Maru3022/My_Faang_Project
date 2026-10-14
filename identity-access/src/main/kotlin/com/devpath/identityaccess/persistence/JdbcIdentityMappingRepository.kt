package com.devpath.identityaccess.persistence

import com.devpath.identityaccess.application.port.IdentityMappingRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class JdbcIdentityMappingRepository(private val jdbc: JdbcTemplate) : IdentityMappingRepository {

    override fun findByProviderAndSubject(provider: String, subject: String): UUID? =
        jdbc.query(
            "SELECT user_id FROM identity.identity_mapping WHERE provider = ? AND subject = ?",
            { rs, _ -> rs.getObject("user_id", UUID::class.java) },
            provider,
            subject,
        ).firstOrNull()

    override fun insert(provider: String, subject: String, userId: UUID) {
        jdbc.update(
            "INSERT INTO identity.identity_mapping (provider, subject, user_id) VALUES (?, ?, ?)",
            provider,
            subject,
            userId,
        )
    }
}