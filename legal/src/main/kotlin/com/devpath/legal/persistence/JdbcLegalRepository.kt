package com.devpath.legal.persistence

import com.devpath.legal.LegalDocumentType
import com.devpath.legal.application.port.LegalRepository
import com.devpath.sharedkernel.UserId
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcLegalRepository(private val jdbc: JdbcTemplate) : LegalRepository {

    override fun currentVersion(type: LegalDocumentType): Int? =
        jdbc.query(
            "SELECT version FROM legal.document_version WHERE document_type = ?",
            { rs, _ -> rs.getInt("version") },
            type.name,
        ).firstOrNull()

    override fun acceptedVersion(userId: UserId, type: LegalDocumentType): Int? =
        jdbc.query(
            "SELECT version FROM legal.user_acceptance WHERE user_id = ? AND document_type = ?",
            { rs, _ -> rs.getInt("version") },
            userId.value,
            type.name,
        ).firstOrNull()

    override fun upsertAcceptance(userId: UserId, type: LegalDocumentType, version: Int) {
        jdbc.update(
            """
            INSERT INTO legal.user_acceptance (user_id, document_type, version)
            VALUES (?, ?, ?)
            ON CONFLICT (user_id, document_type)
            DO UPDATE SET version = EXCLUDED.version, accepted_at = now()
            """.trimIndent(),
            userId.value,
            type.name,
            version,
        )
    }
}