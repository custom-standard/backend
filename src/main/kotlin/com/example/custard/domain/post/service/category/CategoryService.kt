package com.example.custard.domain.post.service.category

import com.example.custard.domain.post.dto.info.CategoryInfo
import com.example.custard.domain.post.dto.response.CategoryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService (
    private val categoryStore: CategoryStore
) {
    fun getCategories(): List<CategoryResponse> {
        val categories = categoryStore.getCategories()
        return categories.map { category -> CategoryResponse.of(category) }
    }

    // TODO: admin only
    @Transactional
    fun createCategory(info: CategoryInfo): CategoryResponse {
        val category = CategoryInfo.toEntity(info)
        return CategoryResponse.of(categoryStore.saveCategory(category))
    }

    @Transactional
    fun updateCategory(categoryId: Long, info: CategoryInfo): CategoryResponse {
        val category = categoryStore.getCategory(categoryId)
        category.updateCategory(info.name, info.description)
        return CategoryResponse.of(category)
    }

    @Transactional
    fun deleteCategory(categoryId: Long) {
        val category = categoryStore.getCategory(categoryId)
        categoryStore.deleteCategory(category)
    }
}