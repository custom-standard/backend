package com.example.custard.domain.proposal.repository

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.proposal.exception.NoSuchProposalException
import com.example.custard.domain.proposal.model.Proposal
import com.example.custard.domain.proposal.service.ProposalStore
import org.springframework.stereotype.Component

@Component
class ProposalStoreImpl (
    private val proposalRepository: ProposalRepository,
) : ProposalStore {
    override fun getProposalById(proposalId: Long): Proposal {
        return proposalRepository.findById(proposalId)
            .orElseThrow { NoSuchProposalException("존재하지 않는 제안입니다.") }
    }

    override fun getProposalByOrder(order: Order): List<Proposal> {
        return proposalRepository.findByOrder(order)
    }

    override fun saveProposal(proposal: Proposal): Proposal {
        return proposalRepository.save(proposal)
    }

    override fun deleteProposal(proposal: Proposal) {
        proposalRepository.delete(proposal)
    }
}