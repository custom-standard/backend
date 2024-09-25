package com.example.custard.domain.order.dto.info

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.order.model.OrderSchedule
import java.time.LocalDate
import java.time.LocalTime

class OrderDateInfo (
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun toEntity(info: OrderDateInfo, order: Order): OrderSchedule {
            return OrderSchedule(order, info.date, info.time)
        }
    }
}