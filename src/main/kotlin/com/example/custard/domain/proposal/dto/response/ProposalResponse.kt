package com.example.custard.domain.proposal.dto.response

import com.example.custard.domain.proposal.enums.ProposalStatus
import com.example.custard.domain.proposal.model.Proposal
import com.example.custard.domain.user.dto.response.UserResponse
import java.time.LocalDateTime

class ProposalResponse (
    val proposalId: Long,
    val sender: UserResponse,
    val price: Int,
    val date: ProposalDateResponse,
    val message: String?,
    val status: ProposalStatus,
) {
    companion object {
        fun of(proposal: Proposal): ProposalResponse {
            return ProposalResponse(
                proposalId = proposal.id,
                sender = UserResponse.of(proposal.sender),
                price = proposal.price,
                date = ProposalDateResponse(proposal.date.date, proposal.date.time),
                message = proposal.message,
                status = proposal.status,
            )
        }
    }
}