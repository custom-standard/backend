package com.example.custard.domain.proposal.dto.request

import com.example.custard.domain.common.date.dto.DateRequest
import com.example.custard.domain.proposal.dto.info.ProposalCreateInfo

class ProposalCreateRequest (
    val orderId: Long,
    val price: Int,
    val date: DateRequest,
    val message: String,
) {
    fun createInfo(): ProposalCreateInfo {
        return ProposalCreateInfo(orderId, price, date.createInfo(), message)
    }
}