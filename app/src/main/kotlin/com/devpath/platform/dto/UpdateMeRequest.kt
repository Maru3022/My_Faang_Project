package com.devpath.platform.dto

import com.devpath.sharedkernel.DomainErrorStatus
import com.devpath.sharedkernel.DomainException
import com.devpath.userprofile.UpdateProfileCommand
import java.time.ZoneId
import java.util.Locale

data class UpdateMeRequest(
    val displayName: String? = null,
    val locale: String? = null,
    val timezone: String? = null,
    val dailyMinutes: Int? = null,
) {
    fun toCommand(): UpdateProfileCommand {
        if (displayName != null && displayName.length > 100) {
            throw DomainException(DomainErrorStatus.BAD_REQUEST, "display_name_too_long", "display name is limited to 100 characters")
        }
        if (dailyMinutes != null && (dailyMinutes < 5 || dailyMinutes > 480)) {
            throw DomainException(DomainErrorStatus.BAD_REQUEST, "invalid_daily_minutes", "daily minutes must be between 5 and 480")
        }
        return UpdateProfileCommand(
            displayName = displayName,
            locale = locale?.let(::parseLocale),
            timezone = timezone?.let(::parseZone),
            dailyMinutes = dailyMinutes,
        )
    }

    private fun parseLocale(value: String): Locale {
        if (!value.matches(Regex("[a-zA-Z]{2,8}(-[a-zA-Z0-9]{2,8})*"))) {
            throw DomainException(DomainErrorStatus.BAD_REQUEST, "invalid_locale", "invalid locale: $value")
        }
        return Locale.forLanguageTag(value)
    }

    private fun parseZone(value: String): ZoneId =
        try {
            ZoneId.of(value)
        } catch (e: Exception) {
            throw DomainException(DomainErrorStatus.BAD_REQUEST, "invalid_timezone", "invalid timezone: $value")
        }
}