package com.example.custard.domain.proposal.model

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.common.date.Date
import com.example.custard.domain.proposal.enums.ProposalStatus
import com.example.custard.domain.proposal.exception.InvalidProposalStatusException
import com.example.custard.domain.user.model.User
import jakarta.persistence.*

@Entity
class Proposal(
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

    var status: ProposalStatus = ProposalStatus.PENDING

    fun updateProposalStatus(accept: Boolean) {
        validateIsPending()

        status = if (accept) ProposalStatus.ACCEPTED else ProposalStatus.REJECTED
    }

    fun validateIsPending() {
        if (status != ProposalStatus.PENDING) {
            throw InvalidProposalStatusException("이미 처리된 제안입니다.")
        }
    }
}