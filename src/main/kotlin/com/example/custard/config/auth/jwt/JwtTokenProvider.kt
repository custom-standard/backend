package com.example.custard.config.auth.jwt

import com.example.custard.config.auth.CustomUserDetails
import com.example.custard.config.auth.oauth2.handler.OAuth2LoginSuccessHandler
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

@Component
@Slf4j
class JwtTokenProvider(
    @Value("\${spring.security.auth.secret}")
    secretKey: String,
    @Value("\${spring.security.auth.refresh-cookie-key}")
    cookieKey: String,
) {
    val log: Logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler::class.java)

    companion object {
        val ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60
        val REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7
    }

    @Autowired
    private lateinit var userStore: UserStore

    private val SECRET_KEY: String = Base64.getEncoder().encodeToString(secretKey.toByteArray(Charsets.UTF_8))
    private val AUTHORITIES_KEY = "role"

    fun createAccessToken(authentication: Authentication): String {
        val now: Date = Date()
        val validity: Date = Date(now.time + ACCESS_TOKEN_EXPIRE_LENGTH)

        val user: CustomUserDetails = authentication.principal as CustomUserDetails

        val userId: String = user.getName()
        val role: String = authentication.authorities.stream()
            .map { it.authority }
            .collect(Collectors.joining(","))

        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .setSubject(userId)
            .claim(AUTHORITIES_KEY, role)
            .setIssuer("custard")
            .setIssuedAt(now)
            .setExpiration(validity)
            .compact()
    }

    fun createRefreshToken(authentication: Authentication): String {
        val now: Date = Date()
        val validity: Date = Date(now.time + REFRESH_TOKEN_EXPIRE_LENGTH)

        val token: String = Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .setIssuer("custard")
            .setIssuedAt(now)
            .setExpiration(validity)
            .compact()

        saveRefreshToken(authentication, token)

        return token
    }

    fun saveRefreshToken(authentication: Authentication, token: String) {
        val customUser: CustomUserDetails = authentication.principal as CustomUserDetails
        val user: User = userStore.getById(customUser.getName().toLong())

        user.updateRefreshToken(token)
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims: Claims = parseClaims(accessToken)

        val authorities: Collection<out GrantedAuthority> =
            Arrays.stream(claims[AUTHORITIES_KEY].toString().split(",").toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())

        val principal: CustomUserDetails = CustomUserDetails(
            id = claims.subject as Long,
            email = "",
            authorities = authorities
        )

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    private fun parseClaims(accessToken: String): Claims {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(accessToken)
            .body
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
            return true
        } catch (e: ExpiredJwtException) {
            log.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            log.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: Exception) {
            log.info("유효하지 않은 JWT 토큰입니다.")
        }
        return false
    }
}