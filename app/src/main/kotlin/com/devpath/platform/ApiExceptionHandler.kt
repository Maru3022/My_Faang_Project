package com.devpath.platform

import com.devpath.sharedkernel.DomainErrorStatus
import com.devpath.sharedkernel.DomainException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode

@RestControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun createResponseEntity(body: Any?, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest): ResponseEntity<Any> {
        if (body is ProblemDetail) body.setProperty("requestId", request.getAttribute("requestId", 0))
        return super.createResponseEntity(body, headers, status, request)
    }
    @ExceptionHandler(DomainException::class)
    fun domain(ex: DomainException, request: HttpServletRequest): ProblemDetail =
        ProblemDetail.forStatusAndDetail(statusOf(ex.status), ex.message).apply {
            title = "Domain error"
            setProperty("code", ex.code)
            setProperty("requestId", request.getAttribute("requestId"))
        }
    @ExceptionHandler(Exception::class)
    fun unexpected(ex: Exception, request: HttpServletRequest): ProblemDetail {
        // Do not log exception messages, tokens or request bodies.
        log.error("Unhandled request failure: type={}, requestId={}", ex.javaClass.simpleName, request.getAttribute("requestId"))
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred").apply {
            setProperty("requestId", request.getAttribute("requestId"))
        }
    }
    private fun statusOf(status: DomainErrorStatus): HttpStatus = when (status) {
        DomainErrorStatus.BAD_REQUEST -> HttpStatus.BAD_REQUEST
        DomainErrorStatus.FORBIDDEN -> HttpStatus.FORBIDDEN
        DomainErrorStatus.NOT_FOUND -> HttpStatus.NOT_FOUND
        DomainErrorStatus.CONFLICT -> HttpStatus.CONFLICT
        DomainErrorStatus.UNPROCESSABLE_ENTITY -> HttpStatus.UNPROCESSABLE_ENTITY
    }
}
