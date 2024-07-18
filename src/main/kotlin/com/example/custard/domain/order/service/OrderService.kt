package com.example.custard.domain.order.service

import com.example.custard.domain.order.dto.info.*
import com.example.custard.domain.order.dto.response.*
import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.service.PostStore
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class OrderService (
    private val orderStore: OrderStore,
    private val postStore: PostStore,
    private val userStore: UserStore,
) {
    fun getOrder(userUUID: String, orderId: Long): OrderResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(orderId)

        order.validateParticipant(user)

        return OrderResponse.of(order, order.isRequester(user))
    }

    // 내 거만 (필터: status, 카테고리, 판매자/구매자) 아니면 게시글에 따라서 필터
    fun getOrders(userUUID: String, info: OrderReadInfo): Page<OrderResponse> {
        val user: User = userStore.getByUUID(userUUID)
        val post: Post? = info.postId?.let { postStore.getById(it) }

        if (post != null && !post.isWriter(user)) {
            // TODO: Exception 처리
            throw RuntimeException("해당 게시물에 대한 작성자가 아닙니다.")
        }

        val page: Int = info.page
        val size: Int = info.size
        val position: OrderPosition = info.position

        // 필터 조건
        val status: OrderStatus? = info.status

        val orders: Page<Order> = orderStore.getOrders(page, size, position, user, post, status)
        return orders.map { OrderResponse.of(it, it.isRequester(user)) }
    }

    fun createOrder(userUUID: String, info: OrderCreateInfo): OrderResponse {
        val post: Post = postStore.getById(info.postId)

        val requester: User = userStore.getByUUID(userUUID)
        val responder: User = post.writer

        val order: Order = OrderCreateInfo.toEntity(info, post, requester, responder)

        orderStore.saveOrder(order)

        return OrderResponse.of(order, order.isRequester(requester))
    }

    fun respondOrder(userUUID: String, orderId: Long, accept: Boolean): OrderResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(orderId)

        order.respondOrder(user, accept)

        return OrderResponse.of(order, order.isRequester(user))
    }

    fun updateOrderStatus(userUUID: String, info: OrderUpdateStatusInfo): OrderResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(info.orderId)

        order.validateCreator(user)

        if (info.forward) { order.forwardStatus() } else { order.backwardStatus() }

        return OrderResponse.of(order, order.isRequester(user))
    }

    fun updateOrderData(userUUID: String, info: OrderUpdateDataInfo): OrderResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(info.orderId)

        order.validateCreator(user)
        // TODO: Order에 대해 승낙된 Propose 검증 후 Order 수정

        return OrderResponse.of(order, order.isRequester(user))
    }

    fun deleteOrder(userUUID: String, orderId: Long) {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(orderId)

        order.validateRequester(user)

        orderStore.deleteOrder(order)
    }
}