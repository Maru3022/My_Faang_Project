package com.devpath.userprofile.application.port

import com.devpath.sharedkernel.UserId
import com.devpath.userprofile.UserProfile

/**
 * Persistence port for [UserProfile]. Implemented by the JDBC adapter.
 */
interface UserProfileRepository {
    fun findById(userId: UserId): UserProfile?

    /** Idempotent insert; safe to call when the row already exists. */
    fun insert(profile: UserProfile)

    /**
     * Conditional update guarded by optimistic concurrency on [UserProfile.version].
     * Returns the refreshed profile, or null when the version no longer matches.
     */
    fun update(profile: UserProfile): UserProfile?
}