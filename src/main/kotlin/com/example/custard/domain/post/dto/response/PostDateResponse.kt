package com.example.custard.domain.post.dto.response

import com.example.custard.domain.post.model.date.Date
import java.time.LocalDate
import java.time.LocalTime

class PostDateResponse (
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun of(date: Date): PostDateResponse {
            return PostDateResponse(date.date, date.time)
        }
    }
}