package com.example.custard.domain.order.dto.request

import com.example.custard.domain.order.dto.info.OrderUpdateDataInfo

class OrderUpdateDataRequest (
    val orderId: Long,
    val proposeId: Long,
) {
    fun createInfo(): OrderUpdateDataInfo {
        return OrderUpdateDataInfo(orderId, proposeId)
    }
}