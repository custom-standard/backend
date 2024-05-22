package com.example.custard.config.auth

import com.example.custard.domain.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

class CustomUserDetails (
    val user: User
) : UserDetails, OAuth2User {
    private var attributes: Map<String, Object> = mapOf()

    constructor(user: User, attributes: Map<String, Object>): this(user) {
        this.attributes = attributes
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(GrantedAuthority { user.role.name })
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return user.uuid.toString()
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
        return user.name
    }

    override fun getAttributes(): Map<String, Object> {
        return attributes
    }
}