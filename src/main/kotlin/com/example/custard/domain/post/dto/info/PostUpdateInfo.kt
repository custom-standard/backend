package com.example.custard.domain.post.dto.info

open class PostUpdateInfo (
    val id: Long,
    val categoryId: Long,
    val title: String,
    val description: String,
    val schedules: List<ScheduleInfo>,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
) {
}