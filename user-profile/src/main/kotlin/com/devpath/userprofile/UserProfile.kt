package com.devpath.userprofile

import com.devpath.sharedkernel.UserId
import java.time.ZoneId
import java.util.Locale

/**
 * The user's learning profile. Exposed as the module's public contract; the web
 * layer renders it as an API DTO rather than exposing this type directly.
 *
 * [version] supports optimistic concurrency: an update must be based on the
 * latest version, otherwise the write is rejected.
 */
data class UserProfile(
    val userId: UserId,
    val displayName: String?,
    val locale: Locale,
    val timezone: ZoneId,
    val dailyMinutes: Int?,
    val version: Long = 0,
)