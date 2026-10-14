package com.devpath.identityaccess.application

import com.devpath.identityaccess.IdentityAccessFacade
import com.devpath.identityaccess.ResolvedUser
import com.devpath.identityaccess.UserRegisteredV1
import com.devpath.identityaccess.application.port.IdentityMappingRepository
import com.devpath.sharedkernel.DomainErrorStatus
import com.devpath.sharedkernel.DomainException
import com.devpath.sharedkernel.UserId
import org.springframework.context.ApplicationEventPublisher
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class IdentityAccessService(
    private val repository: IdentityMappingRepository,
    private val eventPublisher: ApplicationEventPublisher,
) : IdentityAccessFacade {

    @Transactional
    override fun resolveUser(provider: String, subject: String): ResolvedUser {
        if (provider.isBlank() || subject.isBlank()) {
            throw DomainException(
                DomainErrorStatus.BAD_REQUEST,
                "invalid_external_identity",
                "an external identity requires a non-blank provider and subject",
            )
        }

        repository.findByProviderAndSubject(provider, subject)?.let {
            return ResolvedUser(UserId(it), newlyRegistered = false)
        }

        val userId = UUID.randomUUID()
        try {
            repository.insert(provider, subject, userId)
        } catch (e: DuplicateKeyException) {
            // A concurrent request mapped the same identity first; reuse it.
            val existing = repository.findByProviderAndSubject(provider, subject) ?: throw e
            return ResolvedUser(UserId(existing), newlyRegistered = false)
        }

        eventPublisher.publishEvent(UserRegisteredV1(provider, subject, userId, Instant.now()))
        return ResolvedUser(UserId(userId), newlyRegistered = true)
    }
}