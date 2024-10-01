package com.example.custard.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Category (
    name: String,
    description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    val id: Long = 0

    @Column(name = "category_name", nullable = false, unique = true)
    var name: String = name
        protected set

    @Column(name = "category_description", nullable = false)
    var description: String = description
        protected set

    fun updateCategory(name: String, description: String) {
        this.name = name
        this.description = description
    }
}