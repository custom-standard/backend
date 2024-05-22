package com.example.custard.domain.post

import com.example.custard.config.auth.oauth2.AuthProvider
import com.example.custard.domain.post.dto.info.*
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.user.model.User
import java.time.LocalDate

class PostFactory {
    val category = Category("TEST CATEGORY", "test category")
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
        posts.add(createPost(PostType.PURCHASE, user, category, "title", "description", true, null, 10000, 20000, null))
        for (i: Int in 1..<n) {
            posts.add(createPost(PostType.PURCHASE, user, category, "title", "description", true, null, 30000, 50000, null))
        }

        return posts
    }

    fun createSalePosts(n: Int): List<Post> {
        val posts: MutableList<Post> = mutableListOf()

        // sale
        posts.add(createPost(PostType.SALE, user, category, "title", "description", true, null, 10000, 20000, "temporary product"))
        for (i: Int in 1..<n) {
            posts.add(createPost(PostType.SALE, user, category, "title", "description", true, null, 30000, 50000, "temporary product"))
        }

        return posts
    }

    fun createPostCreateInfo(type: PostType, categoryId: Long, dates: List<DateInfo> , delivery: Boolean, minPrice: Int, maxPrice: Int): PostCreateInfo {
        return when (type) {
            PostType.PURCHASE -> PostCreateInfo(
                categoryId,
                "title",
                "description",
                dates,
                delivery,
                null,
                minPrice,
                maxPrice,
            )
            PostType.SALE -> PostCreateInfo(
                categoryId,
                "title",
                "description",
                dates,
                delivery,
                null,
                minPrice,
                maxPrice,
                1,
            )
        }
    }

    fun createPostUpdateInfo(type: PostType, id: Long, categoryId: Long, dates: List<DateInfo>, delivery: Boolean, minPrice: Int, maxPrice: Int): PostUpdateInfo {
        return when (type) {
            PostType.PURCHASE -> PostUpdateInfo(
                id,
                categoryId,
                "updated title",
                "updated description",
                dates,
                delivery,
                null,
                minPrice,
                maxPrice,
                null,
            )
            PostType.SALE -> PostUpdateInfo(
                id,
                categoryId,
                "updated title",
                "updated description",
                dates,
                delivery,
                null,
                minPrice,
                maxPrice,
                "updated temporary product"
            )
        }
    }

    fun createPostReadInfo(type: PostType, categoryId: Long?, date: LocalDate?, minPrice: Int?, maxPrice: Int?): PostReadInfo {
        return when (type) {
            PostType.PURCHASE -> PostReadInfo(
                0,
                5,
                type,
                categoryId,
                date,
                minPrice,
                maxPrice
            )
            PostType.SALE -> PostReadInfo(
                0,
                5,
                type,
                categoryId,
                date,
                minPrice,
                maxPrice
            )
        }
    }
}