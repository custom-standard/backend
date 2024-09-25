package com.example.custard.domain.order.dto.response

import com.example.custard.domain.order.model.OrderSchedule
import java.time.LocalDate
import java.time.LocalTime

class OrderDateResponse (
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun of(date: OrderSchedule): OrderDateResponse {
            return OrderDateResponse(date.date, date.time)
        }
    }
}