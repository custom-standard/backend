package com.example.custard.domain.order.dto.response

import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.post.dto.response.DateResponse
import com.example.custard.domain.post.dto.response.PostSimpleResponse
import com.example.custard.domain.user.dto.response.UserResponse

class OrderResponse (
    val orderId: Long,
    val post: PostSimpleResponse,
    val requester: UserResponse,
    val responder: UserResponse,
    val price: Int,
    val date: DateResponse,
    val status: OrderStatus,
    val isRequest: Boolean,
) {
    companion object {
        fun of(order: Order, isRequest: Boolean): OrderResponse {
            return OrderResponse(
                orderId = order.id,
                post = PostSimpleResponse.of(order.post),
                requester = UserResponse.of(order.requester),
                responder = UserResponse.of(order.responder),
                price = order.price,
                date = DateResponse.of(order.date),
                status = order.status,
                isRequest = isRequest
            )
        }
    }
}