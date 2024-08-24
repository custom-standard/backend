package com.example.custard.domain.order.service

import com.example.custard.domain.order.dto.info.*
import com.example.custard.domain.order.dto.response.*
import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.order.model.OrderDate
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.service.PostStore
import com.example.custard.domain.proposal.model.Proposal
import com.example.custard.domain.proposal.service.ProposalStore
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService (
    private val orderStore: OrderStore,
    private val postStore: PostStore,
    private val proposalStore: ProposalStore,
    private val userStore: UserStore,
) {
    fun getOrder(userUUID: String, orderId: Long): OrderResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(orderId)

        order.validateParticipant(user)

        return OrderResponse.of(order, order.isRequester(user))
    }

    fun getOrders(userUUID: String, info: OrderReadInfo): Page<OrderResponse> {
        val user: User = userStore.getByUUID(userUUID)
        val post: Post? = info.postId?.let { postStore.getById(it) }

        post?.let { post.validateWriter(user) }

        val page: Int = info.page
        val size: Int = info.size
        val position: OrderPosition = info.position

        val status: OrderStatus? = info.status

        val orders: Page<Order> = orderStore.getOrders(page, size, position, user, post, status)
        return orders.map { OrderResponse.of(it, it.isRequester(user)) }
    }

    @Transactional
    fun createOrder(userUUID: String, info: OrderCreateInfo): OrderResponse {
        val post: Post = postStore.getById(info.postId)

        val requester: User = userStore.getByUUID(userUUID)
        val responder: User = post.writer

        val order: Order = OrderCreateInfo.toEntity(info, post, requester, responder)
        val dates: List<OrderDate> = info.dates.map { OrderDateInfo.toEntity(it, order) }
        order.updateDates(dates.toMutableList())

        orderStore.saveOrder(order)

        return OrderResponse.of(order, order.isRequester(requester))
    }

    @Transactional
    fun confirmOrder(userUUID: String, orderId: Long, accept: Boolean): OrderResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(orderId)

        order.confirmOrder(user, accept)

        return OrderResponse.of(order, order.isRequester(user))
    }

    @Transactional
    fun updateOrderStatus(userUUID: String, info: OrderUpdateStatusInfo): OrderResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(info.orderId)

        order.validateCreator(user)

        if (info.forward) { order.forwardStatus() } else { order.backwardStatus() }

        return OrderResponse.of(order, order.isRequester(user))
    }

    @Transactional
    fun updateOrderData(userUUID: String, info: OrderUpdateDataInfo): OrderResponse {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(info.orderId)

        order.validateParticipant(user)

        val proposal: Proposal = proposalStore.getProposalById(info.proposeId)

        proposal.validateProposal(order, user)

        val date: OrderDate = OrderDate(order, proposal.date.date, proposal.date.time)

        order.updateOrder(proposal.price, mutableListOf(date))

        return OrderResponse.of(order, order.isRequester(user))
    }

    @Transactional
    fun deleteOrder(userUUID: String, orderId: Long) {
        val user: User = userStore.getByUUID(userUUID)
        val order: Order = orderStore.getById(orderId)

        order.validateRequester(user)

        orderStore.deleteOrder(order)
    }
}