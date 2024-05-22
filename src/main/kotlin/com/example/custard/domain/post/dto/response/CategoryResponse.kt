package com.example.custard.domain.post.dto.response

import com.example.custard.domain.post.model.Category

class CategoryResponse (
    val id: Long,
    val name: String,
    val description: String
) {
    companion object {
        fun of(category: Category): CategoryResponse {
            return CategoryResponse(category.id, category.name, category.description)
        }
    }
}