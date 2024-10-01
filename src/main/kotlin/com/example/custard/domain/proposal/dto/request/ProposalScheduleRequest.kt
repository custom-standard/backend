package com.example.custard.domain.proposal.dto.request

import com.example.custard.domain.proposal.dto.info.ProposalScheduleInfo
import java.time.LocalDate
import java.time.LocalTime

class ProposalScheduleRequest (
    val date: LocalDate,
    val time: LocalTime?,
) {
    fun createInfo(): ProposalScheduleInfo {
        return ProposalScheduleInfo(date, time)
    }
}