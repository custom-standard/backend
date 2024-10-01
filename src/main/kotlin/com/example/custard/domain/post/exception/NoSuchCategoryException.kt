package com.example.custard.domain.post.exception

import com.example.custard.api.exception.BusinessException
import com.example.custard.api.response.ResponseCode

class NoSuchCategoryException (
    message: String?,
) : BusinessException (
    ResponseCode.NOT_FOUND,
    message,
) {
}