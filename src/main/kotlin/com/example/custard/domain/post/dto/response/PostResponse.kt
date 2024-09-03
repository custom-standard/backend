package com.example.custard.domain.post.dto.response

import com.example.custard.domain.common.file.FileResponse
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.user.dto.response.UserResponse

class PostResponse (
    val postId: Long,
    val category: CategoryResponse,
    val type: PostType,
    val title: String,
    val thumbnail: FileResponse?,
    val dates: List<DateResponse>,
    val delivery: Boolean,
    val place: String?,
    val minPrice: Int,
    val maxPrice: Int,
    val writer: UserResponse,
) {
    companion object {
        fun of(post: Post): PostResponse {
            val thumbnail = post.images.firstOrNull()?.file?.let { FileResponse.of(it) }
            return PostResponse(
                postId = post.id,
                category = CategoryResponse.of(post.category),
                type = post.type,
                title = post.title,
                thumbnail = thumbnail,
                dates = post.dates.map { DateResponse.of(it.date) },
                delivery = post.delivery,
                place = post.place,
                minPrice = post.minPrice,
                maxPrice = post.maxPrice,
                writer = UserResponse.of(post.writer),
            )
        }
    }
}