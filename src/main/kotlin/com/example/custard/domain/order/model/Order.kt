package com.example.custard.domain.order.model

import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.order.exception.InvalidOrderStateException
import com.example.custard.domain.order.exception.OrderForbiddenException
import com.example.custard.domain.post.model.Post
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
    requestMessage: String,
    price: Int,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    val post: Post = post

    @ManyToOne(fetch = FetchType.LAZY)
    val requester: User = requester

    @ManyToOne(fetch = FetchType.LAZY)
    val responder: User = responder

    val roleRequester: OrderPosition = roleRequester
    val roleResponder: OrderPosition = roleRequester

    val requestMessage: String = requestMessage

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val images = mutableListOf<OrderImage>()

    var price: Int = price
        protected set

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val dates: MutableList<OrderDate> = mutableListOf()

    var status: OrderStatus = OrderStatus.WAITING

    fun updateDates(dates: MutableList<OrderDate>) {
        this.dates.retainAll(dates)
        this.dates.addAll(dates)
    }

    fun updateImages(images: MutableList<OrderImage>) {
        this.images.retainAll(images)
        this.images.addAll(images)
    }

    fun updateOrder(price: Int, dates: MutableList<OrderDate>) {
        this.price = price
        updateDates(dates)
    }

    fun confirmOrder(responder: User, accept: Boolean) {
        validateResponder(responder)

        if (status == OrderStatus.WAITING) {
            if (accept) { forwardStatus() } else { backwardStatus() }
        } else {
            throw InvalidOrderStateException("이미 ${status.description} 단계인 주문입니다.")
        }
    }

    fun forwardStatus() {
        status = status.stepForward() ?: status
    }

    fun backwardStatus() {
        status = status.stepBackward() ?: status
    }

    fun getOtherUser(user: User): User {
        return if (requester == user) responder else requester
    }

    fun isRequester(user: User): Boolean {
        return requester == user
    }

    private fun isResponder(user: User): Boolean {
        return responder == user
    }

    private fun isCreator(user: User): Boolean {
        return ((requester == user) && (roleRequester == OrderPosition.CREATOR)) ||
                ((responder == user) && (roleResponder == OrderPosition.CREATOR))
    }

    private fun isClient(user: User): Boolean {
        return ((requester == user) && (roleRequester == OrderPosition.CLIENT)) ||
                ((responder == user) && (roleResponder == OrderPosition.CLIENT))
    }

    fun validateParticipant(user: User) {
        if (!isRequester(user) && !isResponder(user)) {
            throw OrderForbiddenException("해당 주문의 참여자가 아닙니다.")
        }
    }

    fun validateCreator(user: User) {
        if (!isCreator(user)) {
            throw OrderForbiddenException("해당 주문에 대한 상품 제작자가 아닙니다.")
        }
    }

    fun validateRequester(user: User) {
        if (requester != user) {
            throw OrderForbiddenException("해당 주문에 대한 요청자가 아닙니다.")
        }
    }

    fun validateResponder(user: User) {
        if (responder != user) {
            throw OrderForbiddenException("해당 주문에 대한 응답자가 아닙니다.")
        }
    }
}