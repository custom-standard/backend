package com.example.custard.api.response

import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class ApiResponse<T> private constructor (
    private val code: ResponseCode,
    private val data: T?,
    private val message: String?
) {
    companion object {
        fun <T> success (data: T): ApiResponse<T> {
            return ApiResponse(ResponseCode.SUCCESS, data, null)
        }

        fun successWithoutData (): ApiResponse<*> {
            return ApiResponse(ResponseCode.SUCCESS, null, null)
        }

        fun exceptionError (code: ResponseCode, message: String): ApiResponse<*> {
            return ApiResponse(code, null, message)
        }

        fun fieldError (bindingResult: BindingResult): ApiResponse<HashMap<String, String>> {
            var errors = HashMap<String, String>()

            bindingResult.fieldErrors.forEach { error ->
                if (error is FieldError) errors[error.field] = error.defaultMessage ?: ""
                else errors[error.objectName] = error.defaultMessage ?: ""
            }

            return ApiResponse(ResponseCode.BAD_REQUEST, errors, null)
        }
    }
}