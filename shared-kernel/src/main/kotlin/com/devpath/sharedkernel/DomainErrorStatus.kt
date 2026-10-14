package com.devpath.sharedkernel

/**
 * HTTP-independent classification of a domain failure. Kept free of any web
 * framework so domain/application code never depends on Spring.
 */
enum class DomainErrorStatus {
    BAD_REQUEST,
    FORBIDDEN,
    NOT_FOUND,
    CONFLICT,
    UNPROCESSABLE_ENTITY,
}