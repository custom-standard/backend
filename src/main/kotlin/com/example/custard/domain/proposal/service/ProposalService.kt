package com.example.custard.domain.proposal.service

import com.example.custard.domain.common.file.File
import com.example.custard.domain.common.file.FileStore
import com.example.custard.domain.order.model.Order
import com.example.custard.domain.order.service.OrderStore
import com.example.custard.domain.proposal.dto.info.ProposalCreateInfo
import com.example.custard.domain.proposal.dto.response.ProposalResponse
import com.example.custard.domain.proposal.model.Proposal
import com.example.custard.domain.proposal.model.ProposalImage
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ProposalService (
    private val proposalStore: ProposalStore,
    private val orderStore: OrderStore,
    private val userStore: UserStore,
    private val fileStore: FileStore,
) {
    fun getProposals(userUUID: String, orderId: Long): List<ProposalResponse> {
        val order: Order = orderStore.getById(orderId)
        val proposals: List<Proposal> = proposalStore.getProposalByOrder(order)

        return proposals.map { ProposalResponse.of(it) }
    }

    @Transactional
    fun confirmProposal(userUUID: String, proposalId: Long, isAccept: Boolean): ProposalResponse {
        val proposal: Proposal = proposalStore.getProposalById(proposalId)
        val user: User = userStore.getByUUID(userUUID)

        proposal.updateProposalStatus(user, isAccept)

        return ProposalResponse.of(proposal)
    }


    @Transactional
    fun createProposal(userUUID: String, info: ProposalCreateInfo, files: List<MultipartFile>): ProposalResponse {
        val order: Order = orderStore.getById(info.orderId)
        val sender: User = userStore.getByUUID(userUUID)
        val receiver: User = order.getOtherUser(sender)

        val proposal: Proposal = proposalStore.saveProposal(ProposalCreateInfo.toEntity(info, order, sender, receiver))
        storeImages(proposal, files)

        return ProposalResponse.of(proposalStore.saveProposal(proposal))
    }

    private fun storeImages(proposal: Proposal, files: List<MultipartFile>) {
        val imagePath: String = "proposal/${proposal.id}"
        val images: List<File> = fileStore.uploadFiles(imagePath, files)

        val proposalImages: List<ProposalImage> = images.map { ProposalImage(proposal, it) }

        proposal.updateImages(proposalImages.toMutableList())
    }

    @Transactional
    fun deleteProposal(userUUID: String, proposalId: Long) {
        val proposal: Proposal = proposalStore.getProposalById(proposalId)
        val user: User = userStore.getByUUID(userUUID)

        proposal.validateSender(user)

        val files: List<File> = proposal.images.map { it.file }
        fileStore.deleteFiles(files)

        proposalStore.deleteProposal(proposal)
    }
}