package com.example.custard.domain.proposal.model

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.proposal.enums.ProposalStatus
import com.example.custard.domain.proposal.exception.InvalidProposalStatusException
import com.example.custard.domain.proposal.exception.ProposalForbiddenException
import com.example.custard.domain.user.model.User
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
class Proposal(
    order: Order,
    sender: User,
    receiver: User,
    price: Int,
    date: LocalDate,
    time: LocalTime?,
    message: String?,
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

    @Embedded
    val date: ProposalDate = ProposalDate(date, time)

    val message: String? = message

    var status: ProposalStatus = ProposalStatus.PENDING

    fun updateProposalStatus(user: User, accept: Boolean) {
        validateIsPending()
        validateReceiver(user)

        status = if (accept) ProposalStatus.ACCEPTED else ProposalStatus.REJECTED
    }

    private fun validateOrder(order: Order) {
        if (this.order != order) {
            throw ProposalForbiddenException("해당 주문에 대한 제안이 아닙니다.")
        }
    }

    fun validateIsPending() {
        if (status != ProposalStatus.PENDING) {
            throw InvalidProposalStatusException("이미 처리된 제안입니다.")
        }
    }

    private fun validateIsAccepted() {
        if (status != ProposalStatus.ACCEPTED) {
            throw InvalidProposalStatusException("수락된 제안이 아닙니다.")
        }
    }

    fun validateSender(user: User) {
        if (sender != user) {
            throw ProposalForbiddenException("제안을 보낸 사용자가 아닙니다.")
        }
    }

    fun validateReceiver(user: User) {
        if (receiver != user) {
            throw ProposalForbiddenException("제안을 받은 사용자가 아닙니다.")
        }
    }

    fun validateProposal(order: Order, user: User) {
        validateOrder(order)
        validateReceiver(user)
        validateIsAccepted()
    }
}