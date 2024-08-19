package com.example.custard.domain.order.dto.response

import com.example.custard.domain.order.model.OrderDate
import java.time.LocalDate
import java.time.LocalTime

class OrderDateResponse (
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun of(date: OrderDate): OrderDateResponse {
            return OrderDateResponse(date.date, date.time)
        }
    }
}