package com.example.custard.domain.post.dto.response

import com.example.custard.domain.post.model.Schedule
import java.time.LocalDate
import java.time.LocalTime

class ScheduleResponse (
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun of(schedule: Schedule): ScheduleResponse {
            return ScheduleResponse(schedule.date, schedule.time)
        }
    }
}