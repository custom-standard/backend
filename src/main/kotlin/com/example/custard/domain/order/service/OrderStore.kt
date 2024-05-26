package com.example.custard.domain.order.service

import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.user.model.User
import org.springframework.data.domain.Page

interface OrderStore {
    fun getById(orderId: Long): Order

    fun getOrders(page: Int, size: Int, position: OrderPosition, user: User, post: Post?, status: OrderStatus?): Page<Order>

    fun saveOrder(order: Order): Order

    fun deleteOrder(order: Order)
}