package com.example.custard.domain.order.controller

import com.example.custard.api.response.ApiResponse
import com.example.custard.domain.order.dto.request.OrderCreateRequest
import com.example.custard.domain.order.dto.request.OrderReadRequest
import com.example.custard.domain.order.dto.request.OrderUpdateDataRequest
import com.example.custard.domain.order.dto.request.OrderUpdateStatusRequest
import com.example.custard.domain.order.service.OrderService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/order")
class OrderController (
    private val orderService: OrderService,
) {
    @PostMapping("/orders")
    fun getOrders(
        @RequestBody request: OrderReadRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = orderService.getOrders(user.username, request.createInfo())
        return ApiResponse.success(response)
    }


    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable orderId: Long,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = orderService.getOrder(user.username, orderId)
        return ApiResponse.success(response)
    }

    @PostMapping
    fun createOrder(
        @RequestPart(name = "request") request: OrderCreateRequest,
        @RequestPart(name = "files") files: List<MultipartFile>,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = orderService.createOrder(user.username, request.createInfo(), files)
        return ApiResponse.success(response)
    }

    @PostMapping("/confirm")
    fun confirmOrder(
        @RequestParam orderId: Long,
        @RequestParam accept: Boolean,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = orderService.confirmOrder(user.username, orderId, accept)
        return ApiResponse.success(response)
    }

    @PatchMapping("/update/status")
    fun updateOrderStatus(
        @RequestBody request: OrderUpdateStatusRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = orderService.updateOrderStatus(user.username, request.createInfo())
        return ApiResponse.success(response)
    }

    @PatchMapping("/update/data")
    fun updateOrderData(
        @RequestBody request: OrderUpdateDataRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = orderService.updateOrderData(user.username, request.createInfo())
        return ApiResponse.success(response)
    }

    @DeleteMapping
    fun deleteOrder(
        @RequestParam orderId: Long,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        orderService.deleteOrder(user.username, orderId)
        return ApiResponse.successWithoutData()
    }
}