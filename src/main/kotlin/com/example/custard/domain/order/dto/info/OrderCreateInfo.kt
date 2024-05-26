package com.example.custard.domain.order.dto.info

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.post.dto.info.DateInfo
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.user.model.User

class OrderCreateInfo (
    val postId: Long,
    val price: Int,
    val date: DateInfo,
) {
    companion object {
        fun toEntity(info: OrderCreateInfo, post: Post, requester: User, responder: User): Order {
            return Order(post, requester, responder, info.price, DateInfo.toEntity(info.date))
        }
    }
}