package com.example.custard.domain.proposal.service

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.order.service.OrderStore
import com.example.custard.domain.proposal.dto.info.ProposalCreateInfo
import com.example.custard.domain.proposal.dto.response.ProposalResponse
import com.example.custard.domain.proposal.model.Proposal
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProposalService (
    private val proposalStore: ProposalStore,
    private val orderStore: OrderStore,
    private val userStore: UserStore,
) {
    fun getProposals(userUUID: String, orderId: Long): List<ProposalResponse> {
        val order: Order = orderStore.getById(orderId)
        val proposals: List<Proposal> = proposalStore.getProposalByOrder(order)

        return proposals.map { ProposalResponse.of(it) }
    }

    @Transactional
    fun processProposal(userUUID: String, proposalId: Long, isAccept: Boolean): ProposalResponse {
        val proposal: Proposal = proposalStore.getProposalById(proposalId)
        val user: User = userStore.getByUUID(userUUID)

        proposal.updateProposalStatus(user, isAccept)

        return ProposalResponse.of(proposal)
    }


    @Transactional
    fun createProposal(userUUID: String, info: ProposalCreateInfo): ProposalResponse {
        val order: Order = orderStore.getById(info.orderId)
        val sender: User = userStore.getByUUID(userUUID)
        val receiver: User = order.getOtherUser(sender)

        val proposal: Proposal = ProposalCreateInfo.toEntity(info, order, sender, receiver)

        return ProposalResponse.of(proposalStore.saveProposal(proposal))
    }

    @Transactional
    fun deleteProposal(userUUID: String, proposalId: Long) {
        val proposal: Proposal = proposalStore.getProposalById(proposalId)
        val user: User = userStore.getByUUID(userUUID)

        proposal.validateSender(user)

        proposalStore.deleteProposal(proposal)
    }
}