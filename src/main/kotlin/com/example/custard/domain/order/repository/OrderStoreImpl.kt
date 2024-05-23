package com.example.custard.domain.order.repository

import com.example.custard.domain.order.service.OrderStore
import org.springframework.stereotype.Component

@Component
class OrderStoreImpl (
    private val orderRepository: OrderRepository
) : OrderStore {
}