package com.example.custard.domain.order.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class InvalidOrderStateException(
    message: String?
): BusinessException(
    ResponseCode.BAD_REQUEST,
    message
) {
}