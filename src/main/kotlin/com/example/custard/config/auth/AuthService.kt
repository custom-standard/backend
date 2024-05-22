package com.example.custard.config.auth

import com.example.custard.config.auth.jwt.JwtTokenProvider
import com.example.custard.config.auth.jwt.JwtTokenProvider.Companion.ACCESS_TOKEN_EXPIRE_LENGTH
import com.example.custard.config.auth.jwt.JwtTokenProvider.Companion.REFRESH_TOKEN_EXPIRE_LENGTH
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val userStore: UserStore,
    private val tokenProvider: JwtTokenProvider,
) {
    @Value("\${spring.security.auth.access-cookie-key}")
    lateinit var accessTokenName: String
    @Value("\${spring.security.auth.refresh-cookie-key}")
    lateinit var refreshTokenName: String

    fun reissueToken(request: HttpServletRequest, response: HttpServletResponse, token: String) {
        val oldRefreshToken: String = CookieUtils.getCookie(request, refreshTokenName)?.value
            ?: throw RuntimeException("Refresh token is not found")

        if (!tokenProvider.validateToken(oldRefreshToken)) {
            throw RuntimeException("Refresh token is not valid")
        }

        val authentication: Authentication = tokenProvider.getAuthentication(oldRefreshToken)
        val customUser: CustomUserDetails = authentication.principal as CustomUserDetails

        val user: User = userStore.getById(customUser.getName().toLong())

        val savedToken: String? = user.refreshToken
        if (savedToken != oldRefreshToken) {
            throw RuntimeException("Refresh token is not valid")
        }

        val accessToken: String = tokenProvider.createAccessToken(authentication)
        val refreshToken: String = tokenProvider.createRefreshToken(authentication)

        CookieUtils.addCookie(response, refreshTokenName, refreshToken, REFRESH_TOKEN_EXPIRE_LENGTH.toInt())
        CookieUtils.addCookie(response, accessTokenName, accessToken, ACCESS_TOKEN_EXPIRE_LENGTH.toInt())
    }
}