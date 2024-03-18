package com.example.custard.config.auth

import com.example.custard.domain.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

class CustomUserDetails (
    val id: Long,
    private val email: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails, OAuth2User {
    private var attributes: Map<String, Object> = mapOf()

    companion object {
        private fun create(user: User): CustomUserDetails {
            val authorities: List<GrantedAuthority> = Collections.singletonList(SimpleGrantedAuthority("ROLE_USER"))

            return CustomUserDetails(
                user.id,
                user.email,
                authorities
            )
        }

        fun create(user: User, attributes: Map<String, Object>): CustomUserDetails {
            val userDetails: CustomUserDetails = CustomUserDetails.create(user);
            userDetails.setAttributes(attributes)
            return userDetails
        }
    }
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getName(): String {
        return id.toString()
    }

    override fun getAttributes(): Map<String, Object> {
        return attributes
    }

    fun setAttributes(attributes: Map<String, Object>) {
        this.attributes = attributes
    }
}