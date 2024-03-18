package com.example.custard.domain.user.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

enum class Role (
    roleName: String,
) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    val id: Long? = null

    @Column(name = "role_name", nullable = false, unique = true)
    val roleName: String = roleName
}