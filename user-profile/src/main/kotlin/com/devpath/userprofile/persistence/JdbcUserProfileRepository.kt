package com.devpath.userprofile.persistence

import com.devpath.sharedkernel.UserId
import com.devpath.userprofile.UserProfile
import com.devpath.userprofile.application.port.UserProfileRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.ZoneId
import java.util.Locale

@Repository
class JdbcUserProfileRepository(private val jdbc: JdbcTemplate) : UserProfileRepository {

    override fun findById(userId: UserId): UserProfile? =
        jdbc.query(
            """
            SELECT user_id, display_name, locale, timezone, daily_minutes, version
            FROM profile.user_profile
            WHERE user_id = ?
            """.trimIndent(),
            { rs, _ ->
                UserProfile(
                    userId = UserId(rs.getObject("user_id", java.util.UUID::class.java)),
                    displayName = rs.getString("display_name"),
                    locale = Locale.forLanguageTag(rs.getString("locale")),
                    timezone = ZoneId.of(rs.getString("timezone")),
                    dailyMinutes = rs.getObject("daily_minutes", Int::class.javaObjectType),
                    version = rs.getLong("version"),
                )
            },
            userId.value,
        ).firstOrNull()

    override fun insert(profile: UserProfile) {
        jdbc.update(
            """
            INSERT INTO profile.user_profile (user_id, display_name, locale, timezone, daily_minutes, version)
            VALUES (?, ?, ?, ?, ?, 0)
            ON CONFLICT (user_id) DO NOTHING
            """.trimIndent(),
            profile.userId.value,
            profile.displayName,
            profile.locale.toLanguageTag(),
            profile.timezone.id,
            profile.dailyMinutes,
        )
    }

    override fun update(profile: UserProfile): UserProfile? {
        val updated = jdbc.update(
            """
            UPDATE profile.user_profile
            SET display_name = ?, locale = ?, timezone = ?, daily_minutes = ?,
                version = version + 1, updated_at = now()
            WHERE user_id = ? AND version = ?
            """.trimIndent(),
            profile.displayName,
            profile.locale.toLanguageTag(),
            profile.timezone.id,
            profile.dailyMinutes,
            profile.userId.value,
            profile.version,
        )
        if (updated != 1) return null
        return findById(profile.userId)
    }
}