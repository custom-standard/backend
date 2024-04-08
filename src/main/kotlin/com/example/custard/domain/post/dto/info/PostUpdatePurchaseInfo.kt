package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.PostType
import java.time.LocalDate

class PostUpdatePurchaseInfo (
    type: PostType,
    id: Long,
    category: Category,
    title: String,
    description: String,
    startDate: LocalDate,
    endDate: LocalDate,
    delivery: Boolean,
    place: String?,
    minPrice: Int,
    maxPrice: Int
) : PostUpdateInfo(type, id, category, title, description, startDate, endDate, delivery, place, minPrice, maxPrice) {
}