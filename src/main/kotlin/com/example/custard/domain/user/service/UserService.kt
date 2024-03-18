package com.example.custard.domain.user.service

import org.springframework.stereotype.Service

@Service
class UserService (
    private val userStore: UserStore,
) {
}