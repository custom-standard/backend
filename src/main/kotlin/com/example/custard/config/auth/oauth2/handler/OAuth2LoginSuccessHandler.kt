package com.example.custard.config.auth.oauth2.handler

import com.example.custard.config.auth.CookieUtils
import com.example.custard.config.auth.jwt.JwtTokenProvider
import com.example.custard.config.auth.jwt.JwtTokenProvider.Companion.ACCESS_TOKEN_EXPIRE_LENGTH
import com.example.custard.config.auth.jwt.JwtTokenProvider.Companion.REFRESH_TOKEN_EXPIRE_LENGTH
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.util.*

@Component
@Slf4j
class OAuth2LoginSuccessHandler (
    private val tokenProvider: JwtTokenProvider,
) : SimpleUrlAuthenticationSuccessHandler() {
    val log: Logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler::class.java)

    @Value("\${spring.security.auth.access-cookie-key}")
    lateinit var accessTokenName: String
    @Value("\${spring.security.auth.refresh-cookie-key}")
    lateinit var refreshTokenName: String

    @Value("\${spring.security.oauth2.authorizedRedirectUri}")
    lateinit var redirectUri: String

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        println("OAuth2LoginSuccessHandler.onAuthenticationSuccess")
        clearAuthenticationAttributes(request, response)
        val tokens: Pair<String, String> = createTokens(request, response, authentication)

        redirectStrategy.sendRedirect(request, response, redirectUri)
    }

    private fun createTokens(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ): Pair<String, String> {
        if (authentication == null) {
            log.error("Authentication is null")
            throw IllegalArgumentException("Authentication is null")
        }

        val accessToken: String = tokenProvider.createAccessToken(authentication)
        val refreshToken: String = tokenProvider.createRefreshToken(authentication)

        storeTokensInCookie(request, response, accessToken, refreshToken)

        return Pair(accessToken, refreshToken)
    }

    private fun clearAuthenticationAttributes(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
    ) {
        super.clearAuthenticationAttributes(request)
    }

    private fun storeTokensInCookie(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessToken: String,
        refreshToken: String,
    ) {
        CookieUtils.addCookie(response, accessTokenName, accessToken, ACCESS_TOKEN_EXPIRE_LENGTH.toInt())
        CookieUtils.addCookie(response, refreshTokenName, refreshToken, REFRESH_TOKEN_EXPIRE_LENGTH.toInt())
    }
}