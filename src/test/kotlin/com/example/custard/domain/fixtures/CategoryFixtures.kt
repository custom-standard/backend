package com.example.custard.domain.fixtures

import com.example.custard.domain.post.dto.response.CategoryResponse
import com.example.custard.domain.post.model.Category

class CategoryFixtures {
    companion object {
        private const val foodCategoryName = "식품"
        private const val foodCategoryDescription = "수제 식품에 대한 상품입니다."

        private const val generalCategoryName = "잡화"
        private const val generalCategoryDescription = "커스텀 잡화에 대한 상품입니다."

        private const val electronicCategoryName = "전자제품"
        private const val electronicCategoryDescription = "전자제품 관련 커스텀 상품입니다."

        val foodCategory: Category = Category(foodCategoryName, foodCategoryDescription)
        val generalCategory: Category = Category(generalCategoryName, generalCategoryDescription)
        val electronicCategory: Category = Category(electronicCategoryName, electronicCategoryDescription)

        val foodCategoryResponse: CategoryResponse = CategoryResponse.of(foodCategory)
        val generalCategoryResponse: CategoryResponse = CategoryResponse.of(generalCategory)
        val electronicCategoryResponse: CategoryResponse = CategoryResponse.of(electronicCategory)
    }
}