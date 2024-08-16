package com.example.custard.domain.post.dto.response

import com.example.custard.domain.post.model.Date
import java.time.LocalDate
import java.time.LocalTime

class DateResponse (
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun of(date: Date): DateResponse {
            return DateResponse(date.date, date.time)
        }
    }
}