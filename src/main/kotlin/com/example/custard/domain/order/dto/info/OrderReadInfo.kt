package com.example.custard.domain.order.dto.info

import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus

class OrderReadInfo (
    val page: Int,
    val size: Int,
    val postId: Long?,
    val position: OrderPosition,
    val status: OrderStatus?,
) {
}