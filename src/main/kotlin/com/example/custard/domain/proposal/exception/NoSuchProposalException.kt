package com.example.custard.domain.proposal.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class NoSuchProposalException (
    message: String?
) : BusinessException(
    ResponseCode.NOT_FOUND,
    message,
) {
}