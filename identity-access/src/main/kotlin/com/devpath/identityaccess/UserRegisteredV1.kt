package com.devpath.identityaccess

import java.time.Instant
import java.util.UUID

/**
 * Published once, after an external identity is first mapped to an internal
 * user. Consumers must treat handling as at-least-once and stay idempotent.
 */
data class UserRegisteredV1(
    val provider: String,
    val subject: String,
    val userId: UUID,
    val occurredAt: Instant,
)