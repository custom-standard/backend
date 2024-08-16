package com.example.custard.domain.post.dto.info

import com.example.custard.domain.common.date.dto.DateInfo

open class PostUpdateInfo (
    val id: Long,
    val categoryId: Long,
    val title: String,
    val description: String,
    val dates: List<DateInfo>,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
) {
}