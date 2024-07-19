package com.example.custard.domain.order.repository

import com.example.custard.domain.order.enums.OrderPosition
import com.example.custard.domain.order.enums.OrderStatus
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.order.model.QOrder
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.user.model.User
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class OrderCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : OrderCustomRepository {
    override fun findAllOrder(pageable: Pageable,
                              position: OrderPosition,
                              user: User,
                              post: Post?,
                              status: OrderStatus?): Page<Order> {
        val qOrder = QOrder.order

        val clientCondition: BooleanExpression? = when (post?.type) {
            PostType.PURCHASE -> qOrder.responder.eq(user)
            PostType.SALE -> qOrder.requester.eq(user)
            else -> null
        }

        val creatorCondition: BooleanExpression? = when (post?.type) {
            PostType.PURCHASE -> qOrder.requester.eq(user)
            PostType.SALE -> qOrder.responder.eq(user)
            else -> null
        }

        val query = queryFactory
            .selectFrom(qOrder)
            .where(
                eqUser(user),
                if (position == OrderPosition.CLIENT) clientCondition else creatorCondition,
                eqPost(post),
                eqStatus(status),
            )
            .orderBy(qOrder.id.desc())

        val result = query
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageImpl(result, pageable, query.fetchCount())
    }

    private fun eqPost(post: Post?): BooleanExpression? {
        return post?.let {
            val order = QOrder.order
            return order.post.eq(post)
        }
    }

    private fun eqStatus(status: OrderStatus?): BooleanExpression? {
        return status?.let {
            val order = QOrder.order
            return order.status.eq(status)
        }
    }

    // 유저가 responder, requester 중 포함이 되면 반환
    private fun eqUser(user: User): BooleanExpression? {
        val order = QOrder.order
        return order.responder.eq(user).or(order.requester.eq(user))
    }
}