package com.example.custard.domain.post.repository.category

import com.example.custard.domain.post.exception.DuplicateCategoryException
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

    override fun saveCategory(category: Category): Category {
        checkCategoryNameDuplicated(category.name)
        return categoryRepository.save(category)
    }

    private fun checkCategoryNameDuplicated(name: String) {
        if (categoryRepository.existsByName(name)) {
            throw DuplicateCategoryException("이미 존재하는 카테고리 이름입니다.")
        }
    }

    override fun deleteCategory(category: Category) {
        categoryRepository.delete(category)
    }
}