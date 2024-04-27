package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.PostType
import java.time.LocalDate

open class PostUpdateInfo (
    val id: Long,
    val categoryId: Long,
    val title: String,
    val description: String,
    val dates: List<PostDateInfo>,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
    // *** only for sale post ***
    val product: String?
) {
}