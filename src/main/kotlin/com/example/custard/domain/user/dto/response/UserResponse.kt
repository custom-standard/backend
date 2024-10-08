package com.example.custard.domain.user.dto.response

import com.example.custard.domain.user.model.User

class UserResponse (
    val userUUID: String,
    val email: String,
    val name: String,
    val profileImageUrl: String?,
) {
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                userUUID = user.uuid.toString(),
                email = user.email,
                name = user.name,
                profileImageUrl = user.profileImageUrl,
            )
        }
    }
}