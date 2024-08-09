package com.example.custard.domain.post.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class PostForbiddenException (
    message: String?
) : BusinessException (
    ResponseCode.FORBIDDEN,
    message
) {
}