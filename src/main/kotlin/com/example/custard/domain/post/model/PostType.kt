package com.example.custard.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

enum class PostType (
    typeName: String,
){
    SALE("판매"),
    PURCHASE("구매");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    val id: Long = 0

    @Column(name = "type_name", nullable = false, unique = true)
    val typeName: String = typeName
}