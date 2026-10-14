package com.devpath.legal.application.port

import com.devpath.legal.LegalDocumentType
import com.devpath.sharedkernel.UserId

interface LegalRepository {
    fun currentVersion(type: LegalDocumentType): Int?
    fun acceptedVersion(userId: UserId, type: LegalDocumentType): Int?
    fun upsertAcceptance(userId: UserId, type: LegalDocumentType, version: Int)
}