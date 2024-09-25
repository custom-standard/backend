package com.example.custard.domain.order.dto.request

import com.example.custard.domain.order.dto.info.OrderScheduleInfo
import java.time.LocalDate
import java.time.LocalTime

class OrderDateRequest (
    val date: LocalDate,
    val time: LocalTime?,
) {
    fun createInfo(): OrderScheduleInfo {
        return OrderScheduleInfo(date, time)
    }
}