package com.devpath.legal.application

import com.devpath.legal.LegalDocumentType
import com.devpath.legal.LegalFacade
import com.devpath.legal.application.port.LegalRepository
import com.devpath.sharedkernel.DomainErrorStatus
import com.devpath.sharedkernel.DomainException
import com.devpath.sharedkernel.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LegalService(private val repository: LegalRepository) : LegalFacade {

    override fun currentVersions(): Map<LegalDocumentType, Int> =
        LegalDocumentType.entries.associateWith { repository.currentVersion(it) ?: 0 }

    override fun acceptedVersions(userId: UserId): Map<LegalDocumentType, Int> =
        LegalDocumentType.entries.associateWith { repository.acceptedVersion(userId, it) ?: 0 }

    @Transactional
    override fun accept(userId: UserId, type: LegalDocumentType, version: Int) {
        val current = repository.currentVersion(type)
            ?: throw DomainException(DomainErrorStatus.NOT_FOUND, "legal_document_unknown", "no such legal document")
        if (version != current) {
            throw DomainException(
                DomainErrorStatus.CONFLICT,
                "legal_version_outdated",
                "cannot accept version $version; current version is $current",
            )
        }
        repository.upsertAcceptance(userId, type, version)
    }
}