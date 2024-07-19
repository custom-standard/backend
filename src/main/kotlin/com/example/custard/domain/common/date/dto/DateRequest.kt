package com.example.custard.domain.common.date.dto

import com.example.custard.domain.common.date.dto.DateInfo
import java.time.LocalDate
import java.time.LocalTime

class DateRequest (
    val date: LocalDate,
    val time: LocalTime?,
) {
    fun createInfo(): DateInfo {
        return DateInfo(date, time)
    }
}