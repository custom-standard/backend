package com.example.custard.domain.post.repository.schedule

import com.example.custard.domain.post.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime

@Repository
interface ScheduleRepository : JpaRepository<Schedule, Long> {
    fun existsScheduleByDateAndTime(date: LocalDate, time: LocalTime?): Boolean
    fun findByDateAndTime(date: LocalDate, time: LocalTime?): Schedule?
}