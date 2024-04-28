package com.example.custard.domain.post.dto.request

import com.example.custard.domain.post.dto.info.PostDateInfo
import com.example.custard.domain.post.dto.info.PostUpdateInfo

class PostUpdateRequest (
    val postId: Long,
    val categoryId: Long,
    val title: String,
    val description: String,
    val dates: List<PostDateRequest>,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
    // TODO: Product Entity 생성 후 id로 변경
    val product: String?
) {
    fun createInfo(): PostUpdateInfo {
        val datesInfo: List<PostDateInfo> = dates.map { date -> date.createInfo() }
        return PostUpdateInfo(postId, categoryId, title, description, datesInfo, delivery, place, minPrice, maxPrice, product)
    }
}