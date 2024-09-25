package com.example.custard.domain.post.service.date

import com.example.custard.domain.post.dto.info.ScheduleInfo
import com.example.custard.domain.post.model.Schedule

interface ScheduleStore {
    fun saveScheduleIfNotExists(schedule: Schedule): Schedule
    fun findOrCreateSchedule(schedules: List<ScheduleInfo>): List<Schedule>
    fun deleteSchedule(schedule: Schedule)
    fun deleteSchedules(schedules: List<Schedule>)
}