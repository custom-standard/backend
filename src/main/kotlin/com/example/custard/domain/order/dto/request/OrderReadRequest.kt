package com.example.custard.domain.order.dto.request

import com.example.custard.domain.order.dto.info.OrderReadInfo
import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus

class OrderReadRequest (
    val page: Int,
    val size: Int,
    val postId: Long?,
    val position: OrderPosition,
    val status: OrderStatus?,
) {
    fun createInfo(): OrderReadInfo {
        return OrderReadInfo(page, size, postId, position, status)
    }
}