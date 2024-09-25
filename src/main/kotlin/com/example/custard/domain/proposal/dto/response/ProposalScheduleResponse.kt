package com.example.custard.domain.proposal.dto.response

import java.time.LocalDate
import java.time.LocalTime

class ProposalScheduleResponse (
    val date: LocalDate,
    val time: LocalTime?,
) {
}