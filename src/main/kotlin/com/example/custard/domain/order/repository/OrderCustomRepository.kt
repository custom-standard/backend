package com.example.custard.domain.order.repository

import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.user.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OrderCustomRepository {
    fun findAllOrder(pageable: Pageable,
                     position: OrderPosition,
                     user: User,
                     post: Post?,
                     status: OrderStatus?): Page<Order>
}