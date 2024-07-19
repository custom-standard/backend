package com.example.custard.domain.common.date.dto

import com.example.custard.domain.common.date.Date
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