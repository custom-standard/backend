package com.example.custard.domain.post.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class DuplicateCategoryException (
    message: String?
) : BusinessException (
    ResponseCode.DUPLICATE,
    message,
) {
}