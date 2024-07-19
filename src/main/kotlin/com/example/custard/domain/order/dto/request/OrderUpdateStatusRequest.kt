package com.example.custard.domain.order.dto.request

import com.example.custard.domain.order.dto.info.OrderUpdateStatusInfo

class OrderUpdateStatusRequest (
    val orderId: Long,
    val forward: Boolean
){
    fun createInfo(): OrderUpdateStatusInfo {
        return OrderUpdateStatusInfo(orderId, forward)
    }
}