package com.example.custard.domain.order.dto.info

import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.common.date.dto.DateInfo
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.user.model.User

class OrderCreateInfo (
    val postId: Long,
    val price: Int,
    val date: DateInfo,
) {
    companion object {
        fun toEntity(info: OrderCreateInfo, post: Post, requester: User, responder: User): Order {
            val roleRequester = if (post.isSale()) OrderPosition.CLIENT else OrderPosition.CREATOR
            val roleResponder = if (post.isSale()) OrderPosition.CREATOR else OrderPosition.CLIENT
            return Order(post, requester, responder, roleRequester, roleResponder, info.price, DateInfo.toEntity(info.date))
        }
    }
}