package com.example.custard.config.auth.oauth2

import com.example.custard.config.auth.CustomUserDetails
import com.example.custard.config.auth.oauth2.user.CustomOAuth2User
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService (
    private val userStore: UserStore,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(userRequest)
        return process(userRequest, oAuth2User)
    }

    private fun process(userRequest: OAuth2UserRequest?, oAuth2User: OAuth2User): OAuth2User {
        println("CustomOAuth2UserService.process")
        val registrationId: String? = userRequest?.clientRegistration?.registrationId?.uppercase()
        val authProvider: AuthProvider = AuthProvider.valueOf(registrationId ?: "")
        val userInfo: CustomOAuth2User = CustomOAuth2User.of(authProvider, oAuth2User.attributes as Map<String, Any>)

        var user: User? = userStore.findByEmailAndProvider(userInfo.email, authProvider)

        if (user == null) {
            user = createUser(userInfo, authProvider)
        }

        return CustomUserDetails(user, oAuth2User.attributes as Map<String, Object>)
    }

    private fun createUser(userInfo: CustomOAuth2User, authProvider: AuthProvider): User {
        println("CustomOAuth2UserService.createUser")
        val user: User = User(
            provider = authProvider,
            name = userInfo.name,
            email = userInfo.email,
            profileImageUrl = userInfo.imageUrl,
        )
        return userStore.getOrCreateUser(user)
    }
}