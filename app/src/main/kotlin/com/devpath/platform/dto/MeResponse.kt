package com.devpath.platform.dto

import com.devpath.userprofile.UserProfile
import java.util.UUID

data class MeResponse(
    val userId: UUID,
    val displayName: String?,
    val locale: String,
    val timezone: String,
    val dailyMinutes: Int?,
    val version: Long,
) {
    companion object {
        fun from(p: UserProfile) = MeResponse(
            userId = p.userId.value,
            displayName = p.displayName,
            locale = p.locale.toLanguageTag(),
            timezone = p.timezone.id,
            dailyMinutes = p.dailyMinutes,
            version = p.version,
        )
    }
}