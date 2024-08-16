package com.example.custard.domain.post.service

import com.example.custard.domain.common.date.dto.DateInfo
import com.example.custard.domain.post.dto.info.*
import com.example.custard.domain.post.dto.response.PostDetailResponse
import com.example.custard.domain.post.dto.response.PostResponse
import com.example.custard.domain.post.exception.PostForbiddenException
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.post.model.PostDate
import com.example.custard.domain.post.service.category.CategoryStore
import com.example.custard.domain.post.service.date.PostDateStore
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PostService(
    private val postStore: PostStore,
    private val postDateStore: PostDateStore,
    private val userStore: UserStore,
    private val categoryStore: CategoryStore
) {
    /* 게시글 전체 조회 */
    fun getPosts(userUUID: String?, info: PostReadInfo): List<PostResponse> {
        val user: User? = userUUID?.let { userStore.getByUUID(it) }
        val type: PostType = info.type

        val category: Category? = info.categoryId?.let { categoryStore.getCategory(it) }

        val date: LocalDate? = info.date
        val minPrice: Int? = info.minPrice
        val maxPrice: Int? = info.maxPrice

        return when (type) {
            PostType.PURCHASE -> postStore.findAllPurchasePost(category, date, minPrice, maxPrice)
            PostType.SALE -> postStore.findAllSalePost(category, date, minPrice, maxPrice)
        }.map { post -> PostResponse.of(post) }
    }

    /* 게시글 생성 */
    fun createPost(userUUID: String, info: PostCreateInfo): PostDetailResponse {
        val writer: User = userStore.getByUUID(userUUID)
        val category: Category = categoryStore.getCategory(info.categoryId)

        val post: Post = PostCreateInfo.toEntity(info, category, writer)
        val savedPost: Post = postStore.savePost(post)

        val dates = info.dates
        val dateEntities = dates.map { date -> DateInfo.toEntity(date) }

        postDateStore.savePostDate(post, dateEntities)

        return PostDetailResponse.of(savedPost)
    }

    fun updateDates(post: Post, dates: List<DateInfo>): List<PostDate> {
        val dates = dates.map { date -> DateInfo.toEntity(date) }
        return postDateStore.updatePostDate(post, dates)
    }

    /* 게시글 수정 */
    fun updatePost(userUUID: String, info: PostUpdateInfo): PostDetailResponse {
        val user: User = userStore.getByUUID(userUUID)

        val id: Long = info.id
        val post: Post = postStore.getById(id)

        post.validateWriter(user)

        val category: Category = categoryStore.getCategory(info.categoryId)
        val title: String = info.title
        val description: String = info.description
        val dates: List<DateInfo> = info.dates
        val delivery: Boolean = info.delivery
        val place: String? = info.place
        val minPrice: Int = info.minPrice
        val maxPrice: Int = info.maxPrice

        updateDates(post, dates)
        post.updatePost(category, title, description, delivery, place, minPrice, maxPrice)

        return PostDetailResponse.of(post)
    }

    /* 게시글 삭제 */
    fun deletePost(userUUID: String, id: Long) {
        val user: User = userStore.getByUUID(userUUID)
        val post: Post = postStore.getById(id)

        post.validateWriter(user)

        postDateStore.deleteAllByPost(post)
        postStore.deletePost(post)
    }
}