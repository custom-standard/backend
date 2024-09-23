package com.example.custard.domain.post.service.category;

import com.example.custard.domain.post.model.Category

interface CategoryStore {
    fun getCategories(): List<Category>
    fun getCategory(categoryId: Long): Category

    fun saveCategory(category: Category): Category

    fun deleteCategory(category: Category)
}
