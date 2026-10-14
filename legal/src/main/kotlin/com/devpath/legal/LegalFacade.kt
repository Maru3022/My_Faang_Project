package com.devpath.legal

import com.devpath.sharedkernel.UserId

/**
 * Public contract of the legal module: tracks which version of each legal
 * document (Privacy Policy, Terms of Service) a user has accepted. Acceptance
 * is recorded per user and is never global.
 */
interface LegalFacade {
    /** The currently effective version per document type. */
    fun currentVersions(): Map<LegalDocumentType, Int>

    /** The latest version each document type was accepted by the given user. */
    fun acceptedVersions(userId: UserId): Map<LegalDocumentType, Int>

    /** Records acceptance. Rejects versions that are not the current one. */
    fun accept(userId: UserId, type: LegalDocumentType, version: Int)
}

enum class LegalDocumentType {
    PRIVACY_POLICY,
    TERMS_OF_SERVICE,
}