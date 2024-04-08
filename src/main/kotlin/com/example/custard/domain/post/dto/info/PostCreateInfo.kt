package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.PostType
import java.time.LocalDate

open class PostCreateInfo (
    val type: PostType,
    val category: Category,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int
) {
}