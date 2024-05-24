package com.example.custard.domain.order.service

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.stereotype.Service

@Service
class OrderService (
    private val orderStore: OrderStore,
    private val userStore: UserStore,
) {
    private fun validateUser(user: User, order: Order) {
        if (Order.client != user || Order.creator != user) {
            // TODO: Exception 처리
            throw RuntimeException("해당 주문에 대한 권한이 존재하지 않습니다.")
        }
    }

    fun getOrder(userUUID: String, orderId: Long): OrderDetailResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(orderId)

        validateUser(user, order)

        return OrderDetailResponse.of(order)
    }

    // 내 거만 (필터: status, 카테고리, 판매자/구매자) 아니면 게시글에 따라서 필터
    fun getOrders(userUUID: String, info: OrderReadInfo): List<OrderResponse> {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order? = orderStore.findById(info.orderId)

        if (order != null) { validateUser(user, order) }

        val page: Int = info.page
        val size: Int = info.size
        val position: OrderPosition = info.position

        // 필터 조건
        val status: OrderStatus? = info.status

        val orders: List<Order> = orderStore.getOrders(page, size, position, user, order, status)
        return orders.map { OrderResponse.of(it) }
    }

    fun createOrder(userUUID: String, info: OrderCreateInfo): OrderDetailResponse {
        val user: User = userStore.getByUUID(userUUID)
        val other: User = userStore.getByUUID(info.otherUUID)

        val order: Order = when (info.position) {
            OrderPosition.CLIENT -> order = OrderCreateInfo.toEntity(info, client = user, creator = other)
            OrderPosition.CREATOR -> order = OrderCreateInfo.toEntity(info, client = other, creator = user)
        }

        orderStore.saveOrder(order)

        return OrderDetailResponse.of(order)
    }

    fun updateOrderStatus(userUUID: String, info: OrderUpdateStatusInfo): OrderDetailResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(info.orderId)

        validateUser(user, order)

        order.updateStatus(info.status)

        return OrderDetailResponse.of(order)
    }

    fun deleteOrder(userUUID: String, orderId: Long) {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(orderId)

        validateUser(user, order)

        orderStore.deleteOrder(order)
    }
}