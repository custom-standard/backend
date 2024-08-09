package com.example.custard.domain.post.repository.category

import com.example.custard.domain.post.exception.NoSuchCategoryException
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.service.category.CategoryStore
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class CategoryStoreImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryStore {
    override fun getCategories(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun getCategory(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow { NoSuchCategoryException("존재하지 않는 카테고리입니다.") }
    }
}