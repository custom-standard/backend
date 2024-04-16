package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.PostType
import java.time.LocalDate

open class PostUpdateInfo (
    val type: PostType,
    val id: Long,
    val category: Category,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
    // *** only for sale post ***
    val product: String?
) {
    constructor(id: Long,
                category: Category,
                title: String,
                description: String,
                startDate: LocalDate,
                endDate: LocalDate,
                delivery: Boolean,
                place: String?,
                minPrice: Int,
                maxPrice: Int) : this(PostType.PURCHASE, id, category, title, description, startDate, endDate, delivery, place, minPrice, maxPrice, null)

    constructor(id: Long,
                category: Category,
                title: String,
                description: String,
                startDate: LocalDate,
                endDate: LocalDate,
                delivery: Boolean,
                place: String?,
                minPrice: Int,
                maxPrice: Int,
                product: String?) : this(PostType.SALE, id, category, title, description, startDate, endDate, delivery, place, minPrice, maxPrice, product)
}