package com.example.custard.domain.user.repository

import com.example.custard.config.auth.oauth2.AuthProvider
import com.example.custard.domain.user.exception.NoSuchUserException
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserStoreImpl (
    private val userRepository: UserRepository,
) : UserStore {
    override fun findByEmailAndProvider(email: String, provider: AuthProvider): User? {
        return userRepository.findByEmailAndProvider(email, provider)
    }

    override fun getByEmailAndProvider(email: String, provider: AuthProvider): User {
        return userRepository.findByEmailAndProvider(email, provider)
            ?: throw NoSuchUserException("${provider.name}의 $email 에 대한 회원 정보가 존재하지 않습니다.")
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NoSuchUserException("$id 에 대한 회원 정보가 존재하지 않습니다.") }
    }

    override fun getByUUID(key: String): User {
        val uuid: UUID = UUID.fromString(key)
        return userRepository.findByUuid(uuid)
            ?: throw NoSuchUserException("$key 에 대한 회원 정보가 존재하지 않습니다.")
    }

    override fun getOrCreateUser(user: User): User {
        return userRepository.findByUuid(user.uuid)
            ?: userRepository.save(user)
    }

    override fun saveUser(user: User): User {
        return userRepository.save(user)
    }
}