package com.example.custard.domain.order.dto.response

import com.example.custard.domain.order.model.OrderSchedule
import java.time.LocalDate
import java.time.LocalTime

class OrderScheduleResponse (
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun of(date: OrderSchedule): OrderScheduleResponse {
            return OrderScheduleResponse(date.date, date.time)
        }
    }
}