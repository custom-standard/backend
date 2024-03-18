package com.example.custard.domain.user.service

import com.example.custard.domain.user.model.User

interface UserStore {
    fun findByEmail(email: String): User?

    fun getById(id: Long): User
    fun getByEmail(email: String): User

    fun getOrCreateUser(user: User): User

    fun saveUser(user: User): User
}