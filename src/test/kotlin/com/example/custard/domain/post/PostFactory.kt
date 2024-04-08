package com.example.custard.domain.post

import com.example.custard.config.auth.oauth2.AuthProvider
import com.example.custard.domain.post.dto.info.*
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.user.model.User
import java.time.LocalDate

class PostFactory {
    val user = User(AuthProvider.KAKAO, "test@test.com", "test", "test")

    fun createPostCreateInfo(type: PostType, category: Category, startDate: LocalDate, endDate: LocalDate, delivery: Boolean, minPrice: Int, maxPrice: Int): PostCreateInfo {
        return when (type) {
            PostType.PURCHASE -> PostCreatePurchaseInfo(
                type,
                category,
                "title",
                "description",
                startDate,
                endDate,
                delivery,
                null,
                minPrice,
                maxPrice,
            )
            PostType.SALE -> PostCreateSaleInfo(
                type,
                category,
                "title",
                "description",
                startDate,
                endDate,
                delivery,
                null,
                minPrice,
                maxPrice,
                "temporary product"
            )
        }
    }

    fun createPostUpdateInfo(type: PostType, id: Long, category: Category, startDate: LocalDate, endDate: LocalDate, delivery: Boolean, minPrice: Int, maxPrice: Int): PostUpdateInfo {
        return when (type) {
            PostType.PURCHASE -> PostUpdatePurchaseInfo(
                type,
                id,
                category,
                "updated title",
                "updated description",
                startDate,
                endDate,
                delivery,
                null,
                minPrice,
                maxPrice,
            )
            PostType.SALE -> PostUpdateSaleInfo(
                type,
                id,
                category,
                "updated title",
                "updated description",
                startDate,
                endDate,
                delivery,
                null,
                minPrice,
                maxPrice,
                "updated temporary product"
            )
        }
    }

    fun createPostReadInfo(type: PostType, category: Category?, date: LocalDate?, minPrice: Int?, maxPrice: Int?): PostReadInfo {
        return when (type) {
            PostType.PURCHASE -> PostReadInfo(
                type,
                category,
                date,
                minPrice,
                maxPrice
            )
            PostType.SALE -> PostReadInfo(
                type,
                category,
                date,
                minPrice,
                maxPrice
            )
        }
    }
}