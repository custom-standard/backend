package com.example.custard.domain.order.model

import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.date.Date
import com.example.custard.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order (
    post: Post,
    requester: User,
    responder: User,
    roleRequester: OrderPosition,
    roleResponder: OrderPosition,
    price: Int,
    date: Date,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    /*
     PostType이 PURCHASE일 경우, requester 판매자, responder(게시글 작성자)가 구매자
     PostType이 SALE일 경우, requester 구매자, responder(게시글 작성자)가 판매자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    val post: Post = post

    @ManyToOne(fetch = FetchType.LAZY)
    val requester: User = requester

    @ManyToOne(fetch = FetchType.LAZY)
    val responder: User = responder

    val roleRequester: OrderPosition = roleRequester
    val roleResponder: OrderPosition = roleRequester

    var price: Int = price

    @ManyToOne(fetch = FetchType.LAZY)
    var date: Date = date

    var status: OrderStatus = OrderStatus.WAITING

    fun updateOrder(price: Int, date: Date) {
        this.price = price
        this.date = date
    }

    fun respondOrder(responder: User, accept: Boolean) {
        validateResponder(responder)

        if (status == OrderStatus.WAITING) {
            if (accept) { forwardStatus() } else { backwardStatus() }
        } else {
            throw RuntimeException("이미 ${status.description} 단계인 주문입니다.")
        }
    }

    fun forwardStatus() {
        status = status.stepForward() ?: status
    }

    fun backwardStatus() {
        status = status.stepBackward() ?: status
    }

    fun hasParticipant(user: User): Boolean {
        return requester == user || responder == user
    }

    fun isRequester(user: User): Boolean {
        return requester == user
    }

    fun validateResponder(user: User) {
        if (responder != user) {
            throw RuntimeException("해당 주문에 대한 응답자가 아닙니다.")
        }
    }
}