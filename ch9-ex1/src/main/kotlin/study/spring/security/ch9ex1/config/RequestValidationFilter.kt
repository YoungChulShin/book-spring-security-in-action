package study.spring.security.ch9ex1.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

// jakarta.servlet.Filter
class RequestValidationFilter: Filter {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val requestId = httpRequest.getHeader("Request-Id")

        if (requestId == null || requestId.isBlank()) {
            httpResponse.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        chain.doFilter(request, response)
    }
}