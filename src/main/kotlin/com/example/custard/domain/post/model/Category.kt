package com.example.custard.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

enum class Category (
    title: String,
    description: String,
) {
    // TODO: 카테고리 종류 추가
    FOOD("음식", "음식 관련 게시글");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    val id: Long? = null

    @Column(name = "category_name", nullable = false, unique = true)
    val title: String = title

    @Column(name = "category_description", nullable = false)
    val description: String = description
}