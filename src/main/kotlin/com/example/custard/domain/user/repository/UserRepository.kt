package com.example.custard.domain.user.repository

import com.example.custard.config.auth.oauth2.AuthProvider
import com.example.custard.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmailAndProvider(email: String, provider: AuthProvider): User?
    fun findByUuid(uuid: UUID): User?
}