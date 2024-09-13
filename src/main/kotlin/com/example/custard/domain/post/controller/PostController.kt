package com.example.custard.domain.post.controller;

import com.example.custard.api.response.ApiResponse
import com.example.custard.api.response.ResponseCode
import com.example.custard.domain.post.dto.request.PostCreateRequest
import com.example.custard.domain.post.dto.request.PostReadRequest
import com.example.custard.domain.post.dto.request.PostUpdateRequest
import com.example.custard.domain.post.dto.response.PostResponse
import com.example.custard.domain.post.service.PostService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/post")
class PostController (
    private val postService: PostService,
) {
    /* 게시글 전체 조회 */
    @PostMapping("/posts")
    fun getPosts(
        @RequestBody request: PostReadRequest,
        @AuthenticationPrincipal user: UserDetails?
    ): ApiResponse<*> {
        val response = postService.getPosts(user?.username, request.createInfo())
        return ApiResponse.success(response)
    }

    /* 게시글 생성 */
    @PostMapping
    fun createPost(
        @RequestPart(name = "request") request: PostCreateRequest,
        @RequestPart(name = "files") files: List<MultipartFile>,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = postService.createPost(user.username, request.createInfo(), files)
        return ApiResponse.success(response)
    }

    /* 게시글 수정 */
    @PatchMapping
    fun updatePost(
        @RequestPart(name = "request") request: PostUpdateRequest,
        @RequestPart(name = "files") files: List<MultipartFile>,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        val response = postService.updatePost(user.username, request.createInfo(), files)
        return ApiResponse.success(response)
    }

    /* 게시글 삭제 */
    @DeleteMapping
    fun deletePost(
        @RequestParam postId: Long,
        @AuthenticationPrincipal user: UserDetails
    ): ApiResponse<*> {
        postService.deletePost(user.username, postId)
        return ApiResponse.successWithoutData()
    }
}
