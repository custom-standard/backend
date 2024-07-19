package com.example.custard.domain.post.dto.info

import com.example.custard.domain.common.date.dto.DateInfo
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.user.model.User

class PostCreateInfo (
    val type: PostType,
    val categoryId: Long,
    val title: String,
    val description: String,
    val dates: List<DateInfo>,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
    // *** only for sale post ***
    val product: Long?
) {
    constructor(categoryId: Long,
                title: String,
                description: String,
                dates: List<DateInfo>,
                delivery: Boolean,
                place: String?,
                minPrice: Int,
                maxPrice: Int) : this(PostType.PURCHASE, categoryId, title, description, dates, delivery, place, minPrice, maxPrice, null) {
                }

    constructor(categoryId: Long,
                title: String,
                description: String,
                dates: List<DateInfo>,
                delivery: Boolean,
                place: String?,
                minPrice: Int,
                maxPrice: Int,
                product: Long?): this(PostType.SALE, categoryId, title, description, dates, delivery, place, minPrice, maxPrice, product) {
                }

    companion object {
        fun toEntity(info: PostCreateInfo, category: Category, writer: User): Post {
            return when (info.type) {
                PostType.PURCHASE -> Post(writer, category, info.title, info.description, info.delivery, info.place, info.minPrice, info.maxPrice)
                PostType.SALE -> Post(writer, category, info.title, info.description, info.delivery, info.place, info.minPrice, info.maxPrice, info.product.toString())
            }
        }
    }
}