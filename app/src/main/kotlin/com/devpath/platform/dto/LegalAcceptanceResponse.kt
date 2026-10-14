package com.devpath.platform.dto

import com.devpath.legal.LegalDocumentType

data class LegalAcceptanceResponse(
    val current: Map<LegalDocumentType, Int>,
    val accepted: Map<LegalDocumentType, Int>,
)

data class LegalAcceptRequest(val version: Int)