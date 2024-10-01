package com.example.custard.domain.order.dto.response

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.post.dto.response.PostSimpleResponse
import com.example.custard.domain.user.dto.response.UserResponse

class OrderSimpleResponse (
    val orderId: Long?,
    val post: PostSimpleResponse,
    val requester: UserResponse,
    val responder: UserResponse,
) {
    companion object {
        fun of(order: Order): OrderSimpleResponse {
            return OrderSimpleResponse(
                orderId = order.id,
                post = PostSimpleResponse.of(order.post),
                requester = UserResponse.of(order.requester),
                responder = UserResponse.of(order.responder),
            )
        }
    }
}