package com.example.custard.domain.post.controller

import com.example.custard.domain.post.dto.request.category.CategoryCreateRequest
import com.example.custard.domain.post.dto.request.category.CategoryUpdateRequest
import com.example.custard.domain.post.dto.response.CategoryResponse
import com.example.custard.domain.post.service.category.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/category")
class CategoryController (
    private val categoryService: CategoryService
) {
    @GetMapping
    fun getCategories(): List<CategoryResponse> {
        return categoryService.getCategories()
    }

    // TODO: admin only
    @PostMapping
    fun createCategory(
        @RequestBody request: CategoryCreateRequest
    ): CategoryResponse {
        return categoryService.createCategory(request.createInfo())
    }

    fun updateCategory(
        @RequestBody request: CategoryUpdateRequest
    ): CategoryResponse {
        return categoryService.updateCategory(request.categoryId, request.createInfo())
    }

    fun deleteCategory(categoryId: Long) {
        categoryService.deleteCategory(categoryId)
    }
}