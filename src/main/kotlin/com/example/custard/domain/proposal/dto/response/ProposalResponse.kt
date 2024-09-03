package com.example.custard.domain.proposal.dto.response

import com.example.custard.domain.common.file.FileResponse
import com.example.custard.domain.order.dto.response.OrderSimpleResponse
import com.example.custard.domain.proposal.enums.ProposalStatus
import com.example.custard.domain.proposal.model.Proposal
import com.example.custard.domain.user.dto.response.UserResponse

class ProposalResponse (
    val proposalId: Long,
    val order: OrderSimpleResponse,
    val sender: UserResponse,
    val price: Int,
    val date: ProposalDateResponse,
    val message: String?,
    val images: List<FileResponse>,
    val status: ProposalStatus,
) {
    companion object {
        fun of(proposal: Proposal): ProposalResponse {
            val images = proposal.images.map { FileResponse.of(it.file) }
            return ProposalResponse(
                proposalId = proposal.id,
                order = OrderSimpleResponse.of(proposal.order),
                sender = UserResponse.of(proposal.sender),
                price = proposal.price,
                date = ProposalDateResponse(proposal.date.date, proposal.date.time),
                message = proposal.message,
                images = images,
                status = proposal.status,
            )
        }
    }
}