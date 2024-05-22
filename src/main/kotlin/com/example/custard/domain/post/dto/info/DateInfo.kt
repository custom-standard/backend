package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.date.Date
import java.time.LocalDate
import java.time.LocalTime

class DateInfo(
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun toEntity(info: DateInfo): Date {
            return Date(info.date, info.time)
        }
    }
}