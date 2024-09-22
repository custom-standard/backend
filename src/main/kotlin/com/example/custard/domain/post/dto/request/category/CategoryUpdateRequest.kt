package com.example.custard.domain.post.dto.request.category

import com.example.custard.domain.post.dto.info.CategoryInfo

class CategoryUpdateRequest (
    val categoryId: Long,
    val name: String,
    val description: String
) {
    fun createInfo(): CategoryInfo {
        return CategoryInfo(name, description)
    }
}