package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.Schedule
import java.time.LocalDate
import java.time.LocalTime

class ScheduleInfo(
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun toEntity(info: ScheduleInfo): Schedule {
            return Schedule(info.date, info.time)
        }
    }
}