package com.devpath.userprofile.application

import com.devpath.sharedkernel.DomainErrorStatus
import com.devpath.sharedkernel.DomainException
import com.devpath.sharedkernel.UserId
import com.devpath.userprofile.UpdateProfileCommand
import com.devpath.userprofile.UserProfile
import com.devpath.userprofile.UserProfileFacade
import com.devpath.userprofile.application.port.UserProfileRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.util.Locale

@Service
class UserProfileService(
    private val repository: UserProfileRepository,
) : UserProfileFacade {

    override fun get(userId: UserId): UserProfile =
        repository.findById(userId)
            ?: throw DomainException(DomainErrorStatus.NOT_FOUND, "profile_not_found", "profile not found")

    @Transactional
    override fun ensureProfile(userId: UserId): UserProfile {
        repository.findById(userId)?.let { return it }
        val created = UserProfile(
            userId = userId,
            displayName = null,
            locale = Locale.ENGLISH,
            timezone = ZoneId.of("UTC"),
            dailyMinutes = null,
        )
        try {
            repository.insert(created)
        } catch (e: DuplicateKeyException) {
            return repository.findById(userId) ?: throw e
        }
        return created
    }

    @Transactional
    override fun update(userId: UserId, command: UpdateProfileCommand): UserProfile {
        val current = ensureProfile(userId)
        val updated = current.copy(
            displayName = command.displayName ?: current.displayName,
            locale = command.locale ?: current.locale,
            timezone = command.timezone ?: current.timezone,
            dailyMinutes = command.dailyMinutes ?: current.dailyMinutes,
        )
        return repository.update(updated)
            ?: throw DomainException(
                DomainErrorStatus.CONFLICT,
                "profile_concurrent_modification",
                "profile was modified concurrently; retry with the latest version",
            )
    }
}