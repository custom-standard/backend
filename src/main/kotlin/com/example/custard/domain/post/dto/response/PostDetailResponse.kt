package com.example.custard.domain.post.dto.response

import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.user.dto.response.UserResponse

class PostDetailResponse (
    val postId: Long,
    val category: CategoryResponse,
    val type: PostType,
    val title: String,
    val description: String,
    val dates: List<DateResponse>,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
    // TODO: product entity 추가 후 id로 변경
    val productId: String?,
    val writer: UserResponse
) {
    companion object {
        fun of(post: Post): PostDetailResponse {
            return PostDetailResponse(
                postId = post.id,
                category = CategoryResponse.of(post.category),
                type = post.type,
                title = post.title,
                description = post.description,
                dates = post.dates.map { DateResponse.of(it.date) },
                delivery = post.delivery,
                place = post.place,
                minPrice = post.minPrice,
                maxPrice = post.maxPrice,
                // TODO: product entity 추가 후 id로 변경
                productId = post.product,
                writer = UserResponse.of(post.writer),
            )
        }
    }
}