package com.example.custard.domain.post.model.date

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
class Date (
    date: LocalDate,
    time: LocalTime?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    val date: LocalDate = date

    val time: LocalTime? = time
}