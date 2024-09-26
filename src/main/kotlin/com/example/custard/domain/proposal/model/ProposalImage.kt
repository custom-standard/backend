package com.example.custard.domain.proposal.model

import com.example.custard.domain.common.file.File
import jakarta.persistence.*

@Entity
class ProposalImage (
    proposal: Proposal,
    file: File,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposal_id")
    val proposal: Proposal = proposal

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    val file: File = file
}