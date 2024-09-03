package com.example.custard.domain.order.dto.response

import com.example.custard.domain.common.file.FileResponse
import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.post.dto.response.PostSimpleResponse
import com.example.custard.domain.user.dto.response.UserResponse

class OrderResponse (
    val orderId: Long,
    val post: PostSimpleResponse,
    val requester: UserResponse,
    val responder: UserResponse,
    val requestMessage: String,
    val images: List<FileResponse>,
    val price: Int,
    val dates: List<OrderDateResponse>,
    val status: OrderStatus,
    val isRequest: Boolean,
) {
    companion object {
        fun of(order: Order, isRequest: Boolean): OrderResponse {
            val dates: List<OrderDateResponse> = order.dates.map { OrderDateResponse.of(it) }
            val images: List<FileResponse> = order.images.map { FileResponse.of(it.file) }
            return OrderResponse(
                orderId = order.id,
                post = PostSimpleResponse.of(order.post),
                requester = UserResponse.of(order.requester),
                responder = UserResponse.of(order.responder),
                requestMessage = order.requestMessage,
                images = images,
                price = order.price,
                dates = dates,
                status = order.status,
                isRequest = isRequest
            )
        }
    }
}