package com.devpath.platform

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RequestIdFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestId = request.getHeader("X-Request-ID")
            ?.takeIf { it.matches(Regex("[A-Za-z0-9_-]{1,64}")) } ?: UUID.randomUUID().toString()
        request.setAttribute("requestId", requestId)
        response.setHeader("X-Request-ID", requestId)
        MDC.put("requestId", requestId)
        try { chain.doFilter(request, response) } finally { MDC.remove("requestId") }
    }
}
