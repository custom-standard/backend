package com.example.custard.domain.proposal.dto.info

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.proposal.model.Proposal
import com.example.custard.domain.user.model.User

class ProposalCreateInfo (
    val orderId: Long,
    val price: Int,
    val date: ProposalScheduleInfo,
    val message: String,
) {
    companion object {
        fun toEntity(info: ProposalCreateInfo, order: Order, sender: User, receiver: User): Proposal {
            return Proposal(order, sender, receiver, info.price, info.date.date, info.date.time, info.message)
        }
    }
}