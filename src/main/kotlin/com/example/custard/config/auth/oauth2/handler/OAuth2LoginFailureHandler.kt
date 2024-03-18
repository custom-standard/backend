package com.example.custard.config.auth.oauth2.handler

import com.example.custard.api.response.ApiResponse
import com.example.custard.api.response.ResponseCode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginFailureHandler (
    private val objectMapper: ObjectMapper
) : SimpleUrlAuthenticationFailureHandler() {
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        println("OAuth2LoginFailureHandler.onAuthenticationFailure")
        println(exception?.message)
        exception?.cause?.printStackTrace()
        for (stackTrace in exception?.stackTrace!!) {
            println(stackTrace)
        }
        println(exception?.cause)
        println(exception?.suppressedExceptions)

        val apiResponse: ApiResponse<*> = ApiResponse.exceptionError(ResponseCode.BAD_REQUEST, exception?.localizedMessage!!)

        response?.status = HttpServletResponse.SC_BAD_REQUEST
        response?.contentType = "application/json"
        response?.characterEncoding = "UTF-8"
        response?.writer?.write(objectMapper.writeValueAsString(apiResponse))
//
//        var targetUrl: String = CookieUtils.getCookie(request!!, REDIRECT_URI_PARAM_COOKIE_NAME)
//            ?.value
//            ?: "/"
//
//        targetUrl = UriComponentsBuilder
//            .fromUriString(targetUrl)
//            .queryParam("error", exception?.localizedMessage)
//            .build()
//            .toUriString()
//
//        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
//        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}