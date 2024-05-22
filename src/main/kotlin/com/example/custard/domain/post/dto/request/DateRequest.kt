package com.example.custard.domain.post.dto.request

import com.example.custard.domain.post.dto.info.DateInfo
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