package com.example.custard.domain.order.dto.info

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.order.model.OrderSchedule
import java.time.LocalDate
import java.time.LocalTime

class OrderScheduleInfo (
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun toEntity(info: OrderScheduleInfo, order: Order): OrderSchedule {
            return OrderSchedule(order, info.date, info.time)
        }
    }
}