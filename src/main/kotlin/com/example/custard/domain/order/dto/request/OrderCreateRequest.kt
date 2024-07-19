package com.example.custard.domain.order.dto.request

import com.example.custard.domain.order.dto.info.OrderCreateInfo
import com.example.custard.domain.common.date.dto.DateRequest

class OrderCreateRequest (
    val postId: Long,
    val price: Int,
    val date: DateRequest,
) {
    fun createInfo(): OrderCreateInfo {
        return OrderCreateInfo(postId, price, date.createInfo())
    }
}