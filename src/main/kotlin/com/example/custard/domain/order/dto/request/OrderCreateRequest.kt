package com.example.custard.domain.order.dto.request

import com.example.custard.domain.order.dto.info.OrderCreateInfo
import com.example.custard.domain.order.dto.info.OrderScheduleInfo

class OrderCreateRequest (
    val postId: Long,
    val requestMessage: String,
    val price: Int,
    val schedules: List<OrderScheduleRequest>,
) {
    fun createInfo(): OrderCreateInfo {
        val schedules: List<OrderScheduleInfo> = schedules.map { it.createInfo() }
        return OrderCreateInfo(postId, requestMessage, price, schedules)
    }
}