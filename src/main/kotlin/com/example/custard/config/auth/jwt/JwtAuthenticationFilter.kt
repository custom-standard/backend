package com.example.custard.config.auth.jwt

import com.example.custard.config.auth.oauth2.handler.OAuth2LoginSuccessHandler
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Slf4j
@Component
class JwtAuthenticationFilter (
    private val tokenProvider: JwtTokenProvider,
) : OncePerRequestFilter() {
    val log: Logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = parseBearerToken(request)

        if (token != null && tokenProvider.validateToken(token)) {
            val authentication = tokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        } else {
            log.info("유효한 JWT 토큰이 존재하지 않습니다.")
        }

        filterChain.doFilter(request, response)
    }

    private fun parseBearerToken(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader("Authorization")

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken?.substring(7)
        }
        return null
    }
}