package com.example.custard.domain.proposal.repository

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.proposal.model.Proposal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProposalRepository : JpaRepository<Proposal, Long> {
    fun findByOrder(order: Order): List<Proposal>
}