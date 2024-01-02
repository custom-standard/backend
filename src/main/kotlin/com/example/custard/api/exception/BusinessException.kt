package com.example.custard.api.exception

import com.example.custard.api.response.ResponseCode
import java.lang.RuntimeException

class BusinessException (
    val code: ResponseCode,
    message: String
) : RuntimeException(message ?: code.message) {
}