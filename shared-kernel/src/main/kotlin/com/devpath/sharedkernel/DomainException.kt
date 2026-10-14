package com.devpath.sharedkernel

/**
 * Base exception raised by domain and application code. The [status] classifies
 * the failure independently of the web layer, which maps it onto a concrete HTTP
 * status. [code] is a stable, machine-readable error code used by clients.
 */
open class DomainException(
    val status: DomainErrorStatus,
    val code: String,
    message: String,
) : RuntimeException(message)