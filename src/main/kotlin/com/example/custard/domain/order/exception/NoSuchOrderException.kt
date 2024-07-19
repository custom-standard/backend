package com.example.custard.domain.order.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class NoSuchOrderException (
    message: String?
): BusinessException(
    ResponseCode.NOT_FOUND,
    message,
) {
}