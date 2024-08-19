package com.example.custard.domain.order.dto.request

import com.example.custard.domain.order.dto.info.OrderCreateInfo
import com.example.custard.domain.order.dto.info.OrderDateInfo

class OrderCreateRequest (
    val postId: Long,
    val price: Int,
    val dates: List<OrderDateRequest>,
) {
    fun createInfo(): OrderCreateInfo {
        val dates: List<OrderDateInfo> = dates.map { it.createInfo() }
        return OrderCreateInfo(postId, price, dates)
    }
}