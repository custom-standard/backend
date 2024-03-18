package com.example.custard.domain.user.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class NoSuchUserException (
    message: String?,
): BusinessException(
    ResponseCode.NOT_FOUND,
    message,
) {
}