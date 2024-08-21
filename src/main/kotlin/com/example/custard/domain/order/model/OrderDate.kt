package com.example.custard.domain.order.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
class OrderDate (
    order: Order,
    date: LocalDate,
    time: LocalTime?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

   @ManyToOne(fetch = FetchType.LAZY)
    val order: Order = order

    val date: LocalDate = date

    val time: LocalTime? = time
}