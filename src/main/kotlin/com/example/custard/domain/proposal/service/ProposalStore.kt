package com.example.custard.domain.proposal.service

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.proposal.model.Proposal

interface ProposalStore {
    fun getProposalById(proposeId: Long): Proposal

    fun getProposalByOrder(order: Order): List<Proposal>

    fun saveProposal(proposal: Proposal): Proposal

    fun deleteProposal(proposal: Proposal)
}