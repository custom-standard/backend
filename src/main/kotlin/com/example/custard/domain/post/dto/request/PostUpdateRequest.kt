package com.example.custard.domain.post.dto.request

import com.example.custard.domain.post.dto.info.ScheduleInfo
import com.example.custard.domain.post.dto.info.PostUpdateInfo

class PostUpdateRequest (
    val postId: Long,
    val categoryId: Long,
    val title: String,
    val description: String,
    val schedules: List<ScheduleRequest>,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
) {
    fun createInfo(): PostUpdateInfo {
        val scheduleInfo: List<ScheduleInfo> = schedules.map { date -> date.createInfo() }
        return PostUpdateInfo(postId, categoryId, title, description, scheduleInfo, delivery, place, minPrice, maxPrice)
    }
}