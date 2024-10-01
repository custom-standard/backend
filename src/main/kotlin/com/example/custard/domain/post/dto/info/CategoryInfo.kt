package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.Category

class CategoryInfo (
    val name: String,
    val description: String
) {
    companion object {
        fun toEntity(info: CategoryInfo): Category {
            return Category(info.name, info.description)
        }
    }
}