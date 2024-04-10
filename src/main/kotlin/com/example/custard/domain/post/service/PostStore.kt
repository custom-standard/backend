package com.example.custard.domain.post.service

import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.user.model.User
import java.time.LocalDate

interface PostStore {
    fun savePost(post: Post): Post

    // TODO: 지역 필터링 추가
    fun getById(id: Long): Post
    fun findAllPurchasePost(category: Category?, date: LocalDate?, minPrice: Int?, maxPrice: Int?): List<Post>
    fun findAllSalePost(category: Category?, date: LocalDate?, minPrice: Int?, maxPrice: Int?): List<Post>
    fun findPurchasePostByWriter(writer: User): List<Post>
    fun findSalePostByWriter(writer: User): List<Post>

    fun deletePost(post: Post)
}