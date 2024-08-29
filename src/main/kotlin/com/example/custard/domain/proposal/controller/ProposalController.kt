package com.example.custard.domain.proposal.controller

import com.example.custard.api.response.ApiResponse
import com.example.custard.domain.proposal.dto.request.ProposalCreateRequest
import com.example.custard.domain.proposal.service.ProposalService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/proposal")
class ProposalController (
    private val proposalService: ProposalService
) {
    @GetMapping("/proposals")
    fun getProposals(
        @RequestParam orderId: Long,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = proposalService.getProposals(user.username, orderId)
        return ApiResponse.success(response)
    }

    @PatchMapping("/process")
    fun processProposal(
        @RequestParam proposalId: Long,
        @RequestParam isAccept: Boolean,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = proposalService.processProposal(user.username, proposalId, isAccept)
        return ApiResponse.success(response)
    }

    @PostMapping("/create")
    fun createProposal(
        @RequestPart(name = "request") request: ProposalCreateRequest,
        @RequestPart(name = "files") files: List<MultipartFile>,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = proposalService.createProposal(user.username, request.createInfo(), files)
        return ApiResponse.success(response)
    }

    @DeleteMapping("/delete")
    fun deleteProposal(
        @RequestParam proposalId: Long,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = proposalService.deleteProposal(user.username, proposalId)
        return ApiResponse.successWithoutData()
    }
}