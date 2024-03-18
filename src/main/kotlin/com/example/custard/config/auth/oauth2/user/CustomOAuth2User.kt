package com.example.custard.config.auth.oauth2.user

import com.example.custard.config.auth.oauth2.AuthProvider
import com.example.custard.domain.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

class CustomOAuth2User (
    private val attributes: Map<String, Any>,
    private val id: String,
    val name: String,
    val email: String,
    val imageUrl: String,
) {
    companion object {
        fun of (provider: AuthProvider, attributes: Map<String, Any>): CustomOAuth2User {
            return when (provider) {
                AuthProvider.KAKAO -> ofKakao(attributes)
                AuthProvider.GOOGLE -> ofGoogle(attributes)
                AuthProvider.NAVER -> ofNaver(attributes)
            }
        }

        private fun ofKakao(attributes: Map<String, Any>): CustomOAuth2User {
            val kakaoAccount: Map<String, Any> = attributes["kakao_account"] as Map<String, Any>
            val kakaoProfile: Map<String, Any> = kakaoAccount["profile"] as Map<String, Any>

            return CustomOAuth2User(
                attributes,
                attributes["id"].toString(),
                kakaoProfile["nickname"] as String,
                kakaoAccount["email"] as String,
                kakaoProfile["profile_image_url"] as String
            )
        }

        private fun ofGoogle(attributes: Map<String, Any>): CustomOAuth2User {
            return CustomOAuth2User(
                attributes,
                attributes["sub"].toString(),
                attributes["name"] as String,
                attributes["email"] as String,
                attributes["picture"] as String
            )
        }

        private fun ofNaver(attributes: Map<String, Any>): CustomOAuth2User {
            println("CustomOAuth2User.ofNaver")
            val response: Map<String, Any> = attributes["response"] as Map<String, Any>

            for (attribute in response) {
                println("key: $attribute.key, value: $attribute.value")
            }

            return CustomOAuth2User(
                attributes,
                response["id"].toString(),
                response["nickname"] as String,
                response["email"] as String,
                response["profile_image"] as String
            )
        }
    }
}