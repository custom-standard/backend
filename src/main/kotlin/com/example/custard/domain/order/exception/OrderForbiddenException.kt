package com.example.custard.domain.order.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class OrderForbiddenException(
    message: String?
): BusinessException(
    ResponseCode.FORBIDDEN,
    message
) {
}