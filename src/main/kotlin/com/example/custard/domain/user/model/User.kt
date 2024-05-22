package com.example.custard.domain.user.model

import com.example.custard.config.auth.oauth2.AuthProvider
import jakarta.persistence.Entity
import jakarta.persistence.*
import java.util.UUID

@Entity
class User (
    provider: AuthProvider,
    email: String,
    name: String,
    profileImageUrl: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    val uuid: UUID = UUID.randomUUID()

    val provider: AuthProvider = provider

    val email: String = email

    var name: String = name
        protected set

    var profileImageUrl: String = profileImageUrl
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.USER

    var isSeller: Boolean = false

    var refreshToken: String? = null

    fun updateProfile(name: String, profileImageUrl: String) {
        this.name = name
        this.profileImageUrl = profileImageUrl
    }

    fun updateUserType(isSeller: Boolean) {
        this.isSeller = isSeller
    }

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }
}