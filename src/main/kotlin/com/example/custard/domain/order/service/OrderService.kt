package com.example.custard.domain.order.service

import org.springframework.stereotype.Service

@Service
class OrderService (
    private val orderStore: OrderStore,
) {
}