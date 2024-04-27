package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.date.Date
import java.time.LocalDate
import java.time.LocalTime

class PostDateInfo(
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun toEntity(info: PostDateInfo, post: Post): Date {
            return Date(info.date, info.time)
        }
    }
}