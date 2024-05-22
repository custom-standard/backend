package com.example.custard.domain.user.service

import com.example.custard.config.auth.oauth2.AuthProvider
import com.example.custard.domain.user.model.User
import java.util.UUID

interface UserStore {
    fun findByEmailAndProvider(email: String, provider: AuthProvider): User?

    fun getByEmailAndProvider(email: String, provider: AuthProvider): User
    fun getById(id: Long): User
    fun getByUUID(uuid: String): User

    fun getOrCreateUser(user: User): User

    fun saveUser(user: User): User
}