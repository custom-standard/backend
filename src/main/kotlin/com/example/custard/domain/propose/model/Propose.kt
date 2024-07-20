package com.example.custard.domain.propose.model

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.common.date.Date
import com.example.custard.domain.user.model.User
import jakarta.persistence.*

@Entity
class Propose(
    order: Order,
    sender: User,
    receiver: User,
    price: Int,
    date: Date,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order = order

    @ManyToOne(fetch = FetchType.LAZY)
    val sender: User = sender

    @ManyToOne(fetch = FetchType.LAZY)
    val receiver: User = receiver

    val price: Int = price

    @ManyToOne(fetch = FetchType.LAZY)
    val date: Date = date
}