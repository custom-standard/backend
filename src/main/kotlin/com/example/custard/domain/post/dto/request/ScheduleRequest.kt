package com.example.custard.domain.post.dto.request

import com.example.custard.domain.post.dto.info.ScheduleInfo
import java.time.LocalDate
import java.time.LocalTime

class ScheduleRequest (
    val date: LocalDate,
    val time: LocalTime?,
) {
    fun createInfo(): ScheduleInfo {
        return ScheduleInfo(date, time)
    }
}