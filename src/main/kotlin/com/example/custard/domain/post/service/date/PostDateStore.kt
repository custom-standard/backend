package com.example.custard.domain.post.service.date

import com.example.custard.domain.post.model.Post
import com.example.custard.domain.common.date.Date
import com.example.custard.domain.post.model.PostDate

interface PostDateStore {
    fun existsPostDateByDate(date: Date): Boolean
    fun savePostDate(post: Post, dates: List<Date>): List<PostDate>
    fun updatePostDate(post: Post, dates: List<Date>): List<PostDate>
    fun deletePostDate(postDates: List<PostDate>)
    fun deleteAllByPost(post: Post)
}