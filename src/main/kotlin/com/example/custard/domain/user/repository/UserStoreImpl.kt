package com.example.custard.domain.user.repository

import com.example.custard.domain.user.exception.NoSuchUserException
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.stereotype.Component

@Component
class UserStoreImpl (
    private val userRepository: UserRepository,
) : UserStore {
    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NoSuchUserException("$id 에 대한 회원 정보가 존재하지 않습니다.") }
    }

    override fun getByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw NoSuchUserException("$email 에 대한 회원 정보가 존재하지 않습니다.")
    }

    override fun getOrCreateUser(user: User): User {
        return userRepository.findByEmail(user.email)
            ?: userRepository.save(user)
    }

    override fun saveUser(user: User): User {
        return userRepository.save(user)
    }
}