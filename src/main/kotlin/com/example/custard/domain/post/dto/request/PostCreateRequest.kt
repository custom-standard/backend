package com.example.custard.domain.post.dto.request

import com.example.custard.domain.post.dto.info.ScheduleInfo
import com.example.custard.domain.post.dto.info.PostCreateInfo
import com.example.custard.domain.post.model.PostType

class PostCreateRequest (
    val type: PostType,
    val categoryId: Long,
    val title: String,
    val description: String,
    val schedules: List<ScheduleRequest>,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
    val productId: Long?
) {
    fun createInfo(): PostCreateInfo {
        val scheduleInfo: List<ScheduleInfo> = schedules.map { it.createInfo() }
        return when (type) {
            PostType.PURCHASE -> PostCreateInfo(categoryId, title, description, scheduleInfo, delivery, place, minPrice, maxPrice)
            PostType.SALE -> PostCreateInfo(categoryId, title, description, scheduleInfo, delivery, place, minPrice, maxPrice, productId)
        }
    }
}