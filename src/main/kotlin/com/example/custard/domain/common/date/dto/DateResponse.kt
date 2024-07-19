package com.example.custard.domain.common.date.dto

import com.example.custard.domain.common.date.Date
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