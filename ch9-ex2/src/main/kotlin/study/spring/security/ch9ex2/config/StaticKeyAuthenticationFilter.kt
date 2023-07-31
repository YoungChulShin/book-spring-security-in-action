package study.spring.security.ch9ex2.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class StaticKeyAuthenticationFilter(
    @Value("\${authorization.key}") val authorizationKey: String
): Filter{

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        val authorization = httpRequest.getHeader("Authorization")

        if (authorizationKey == authorization) {
            chain.doFilter(request, response)
        } else {
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }
    }
}