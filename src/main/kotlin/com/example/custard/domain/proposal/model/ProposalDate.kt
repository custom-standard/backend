package com.example.custard.domain.proposal.model

import jakarta.persistence.Embeddable
import java.time.LocalDate
import java.time.LocalTime

@Embeddable
class ProposalDate (
    var date: LocalDate,
    var time: LocalTime?,
) {
}