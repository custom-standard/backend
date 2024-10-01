package com.example.custard.domain.proposal.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class InvalidProposalStatusException (
    message: String?
) : BusinessException(
    ResponseCode.BAD_REQUEST,
    message
) {
}