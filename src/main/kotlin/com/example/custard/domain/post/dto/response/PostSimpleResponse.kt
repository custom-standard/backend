package com.example.custard.domain.post.dto.response

import com.example.custard.domain.common.file.FileResponse
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.user.dto.response.UserResponse

class PostSimpleResponse (
    val postId: Long,
    val type: PostType,
    val category: CategoryResponse,
    val title: String,
    val thumbnail: FileResponse?,
    val writer: UserResponse,
) {
    companion object {
        fun of(post: Post): PostSimpleResponse {
            val thumbnail = post.images.firstOrNull()?.file?.let { FileResponse.of(it) }
            return PostSimpleResponse(post.id, post.type, CategoryResponse.of(post.category), post.title, thumbnail, UserResponse.of(post.writer))
        }
    }
}