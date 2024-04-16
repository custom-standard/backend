package com.example.custard.domain.post

import com.example.custard.config.auth.oauth2.AuthProvider
import com.example.custard.domain.post.dto.info.*
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.user.model.User
import java.time.LocalDate

class PostFactory {
    val user = User(AuthProvider.KAKAO, "test@test.com", "test", "test")

    fun createPost(type: PostType, writer: User, category: Category, title: String, description: String, delivery: Boolean, place: String?, minPrice: Int, maxPrice: Int, product: String?): Post {
        return if (type == PostType.PURCHASE) {
            Post(writer, category, title, description, delivery, place, minPrice, maxPrice)
        } else {
            Post(writer, category, title, description, delivery, place, minPrice, maxPrice, product)
        }
    }

    fun createPosts(n: Int): List<Post> {
        val posts: MutableList<Post> = mutableListOf()

        posts.addAll(createPurchasePosts(n))
        posts.addAll(createSalePosts(n))

        return posts
    }

    fun createPurchasePosts(n: Int): List<Post> {
        val posts: MutableList<Post> = mutableListOf()

        // purchase
        posts.add(createPost(PostType.PURCHASE, user, Category.FOOD, "title", "description", true, null, 10000, 20000, null))
        for (i: Int in 1..<n) {
            posts.add(createPost(PostType.PURCHASE, user, Category.FOOD, "title", "description", true, null, 30000, 50000, null))
        }

        return posts
    }

    fun createSalePosts(n: Int): List<Post> {
        val posts: MutableList<Post> = mutableListOf()

        // sale
        posts.add(createPost(PostType.SALE, user, Category.FOOD, "title", "description", true, null, 10000, 20000, "temporary product"))
        for (i: Int in 1..<n) {
            posts.add(createPost(PostType.SALE, user, Category.FOOD, "title", "description", true, null, 30000, 50000, "temporary product"))
        }

        return posts
    }

    fun createPostCreateInfo(type: PostType, category: Category, startDate: LocalDate, endDate: LocalDate, delivery: Boolean, minPrice: Int, maxPrice: Int): PostCreateInfo {
        return when (type) {
            PostType.PURCHASE -> PostCreateInfo(
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
            PostType.SALE -> PostCreateInfo(
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
            PostType.PURCHASE -> PostUpdateInfo(
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
            PostType.SALE -> PostUpdateInfo(
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