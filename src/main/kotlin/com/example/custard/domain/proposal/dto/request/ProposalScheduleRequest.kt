package com.example.custard.domain.proposal.dto.request

import com.example.custard.domain.proposal.dto.info.ProposalDateInfo
import java.time.LocalDate
import java.time.LocalTime

class ProposalDateRequest (
    val date: LocalDate,
    val time: LocalTime?,
) {
    fun createInfo(): ProposalDateInfo {
        return ProposalDateInfo(date, time)
    }
}