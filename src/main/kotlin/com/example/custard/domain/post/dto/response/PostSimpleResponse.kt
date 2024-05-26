package com.example.custard.domain.post.dto.response

import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType

class PostSimpleResponse (
    val postId: Long,
    val type: PostType,
    val category: CategoryResponse,
    val title: String,
) {
    companion object {
        fun of(post: Post): PostSimpleResponse {
            return PostSimpleResponse(post.id, post.type, CategoryResponse.of(post.category), post.title)
        }
    }
}