package com.example.custard.domain.post.service

import com.example.custard.domain.post.dto.info.*
import com.example.custard.domain.post.model.Post

class PostService(
    private val postStore: PostStore
) {
    /* 게시글 조회 */
    fun getPosts(info: PostReadInfo): List<Post> {
    }

    /* 게시글 생성 */
    fun createPost(info: PostCreateInfo): Post {
    }

    /* 게시글 수정 */
    fun updatePost(info: PostUpdateInfo): Post {
    }

    /* 게시글 삭제 */
    fun deletePost(id: Long): Boolean {
    }
}