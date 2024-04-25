package com.example.custard.domain.post.repository.date

import com.example.custard.domain.post.model.date.Date
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime

@Repository
interface DateRepository : JpaRepository<Date, Long> {
    fun existsDateByDateAndTime(date: LocalDate, time: LocalTime?): Boolean
    fun findByDateAndTime(date: LocalDate, time: LocalTime?): Date?
}