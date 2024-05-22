package com.example.custard.domain.post.repository.date

import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.date.Date
import com.example.custard.domain.post.model.date.PostDate
import org.springframework.data.jpa.repository.JpaRepository

interface PostDateRepository : JpaRepository<PostDate, Long> {
    fun findAllByPost(post: Post): List<PostDate>
    fun existsPostDateByDate(date: Date): Boolean
    fun existsPostDateByPostAndDate(post: Post, date: Date): Boolean
}