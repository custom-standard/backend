package com.example.custard.domain.post.dto.request

import com.example.custard.domain.post.dto.info.PostReadInfo
import com.example.custard.domain.post.model.PostType
import java.time.LocalDate

class PostReadRequest (
    val page: Int,
    val size: Int,
    val type: PostType,
    val categoryId: Long?,
    val date: LocalDate?,
    val minPrice: Int?,
    val maxPrice: Int?
) {
    fun createInfo(): PostReadInfo {
        return PostReadInfo(page, size, type, categoryId, date, minPrice, maxPrice)
    }
}