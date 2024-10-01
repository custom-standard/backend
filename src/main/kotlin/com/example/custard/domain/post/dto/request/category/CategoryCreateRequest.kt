package com.example.custard.domain.post.dto.request.category

import com.example.custard.domain.post.dto.info.CategoryInfo

class CategoryCreateRequest (
    val name: String,
    val description: String
) {
    fun createInfo(): CategoryInfo {
        return CategoryInfo(name, description)
    }
}