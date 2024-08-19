package com.example.custard.domain.order.dto.info

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.order.model.OrderDate
import java.time.LocalDate
import java.time.LocalTime

class OrderDateInfo (
    val date: LocalDate,
    val time: LocalTime?,
) {
    companion object {
        fun toEntity(info: OrderDateInfo, order: Order): OrderDate {
            return OrderDate(order, info.date, info.time)
        }
    }
}