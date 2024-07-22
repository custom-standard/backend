package com.example.custard.domain.propose.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class NoSuchProposeException (
    message: String?
) : BusinessException(
    ResponseCode.NOT_FOUND,
    message,
) {
}