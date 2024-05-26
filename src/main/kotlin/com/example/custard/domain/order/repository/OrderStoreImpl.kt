package com.example.custard.domain.order.repository

import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.order.service.OrderStore
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.user.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class OrderStoreImpl (
    private val orderRepository: OrderRepository,
    private val orderCustomRepository: OrderCustomRepository,
) : OrderStore {
    override fun getById(orderId: Long): Order {
        return orderRepository.findById(orderId)
            // TODO: Exception 처리
            .orElseThrow { RuntimeException("해당 주문이 존재하지 않습니다.") }
    }

    override fun getOrders(
        page: Int,
        size: Int,
        position: OrderPosition,
        user: User,
        post: Post?,
        status: OrderStatus?
    ): Page<Order> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("id").descending())
        return orderCustomRepository.findAllOrder(pageable, position, user, post, status)
    }

    override fun saveOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    override fun deleteOrder(order: Order) {
        orderRepository.delete(order)
    }
}