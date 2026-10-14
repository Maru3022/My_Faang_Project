package com.devpath.userprofile

import com.devpath.sharedkernel.UserId
import java.time.ZoneId
import java.util.Locale

/**
 * Public contract of the user-profile module. All calls are scoped to an
 * explicit [UserId] taken from the verified security context; the caller must
 * never trust a user id supplied in a request body.
 */
interface UserProfileFacade {
    fun get(userId: UserId): UserProfile

    /** Ensures a default profile exists, creating it idempotently if needed. */
    fun ensureProfile(userId: UserId): UserProfile

    fun update(userId: UserId, command: UpdateProfileCommand): UserProfile
}

data class UpdateProfileCommand(
    val displayName: String? = null,
    val locale: Locale? = null,
    val timezone: ZoneId? = null,
    val dailyMinutes: Int? = null,
)