package com.example.custard.domain.post.service

import com.example.custard.domain.post.dto.info.*
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.post.model.date.PostDate
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PostService(
    private val postStore: PostStore,
    private val userStore: UserStore
) {
    /* 게시글 전체 조회 */
    fun getPosts(info: PostReadInfo): List<Post> {
        val type: PostType = info.type

        val category: Category? = info.category
        val date: LocalDate? = info.date
        val minPrice: Int? = info.minPrice
        val maxPrice: Int? = info.maxPrice

        return when (type) {
            PostType.PURCHASE -> postStore.findAllPurchasePost(category, date, minPrice, maxPrice)
            PostType.SALE -> postStore.findAllSalePost(category, date, minPrice, maxPrice)
        }
    }

    /* 게시글 생성 */
    @Transactional
    fun createPost(email: String, info: PostCreateInfo): Post {
        val writer: User = userStore.getByEmail(email)

        val type: PostType = info.type
        val category: Category = info.category
        val title: String = info.title
        val description: String = info.description
        val startDate: LocalDate = info.startDate
        val endDate: LocalDate = info.endDate
        val delivery: Boolean = info.delivery
        val place: String? = info.place
        val minPrice: Int = info.minPrice
        val maxPrice: Int = info.maxPrice
        val product: String? = info.product

        // TODO: PostDate 처리

        return when(type) {
            PostType.PURCHASE -> postStore.savePost(Post(writer, category, title, description, delivery, place, minPrice, maxPrice))
            PostType.SALE -> postStore.savePost(Post(writer, category, title, description, delivery, place, minPrice, maxPrice, product))
        }
    }

    fun updateDates(post: Post, startDate: LocalDate, endDate: LocalDate): MutableList<PostDate> {
        // TODO: PostDate 처리
        return mutableListOf()
    }

    /* 게시글 수정 */
    @Transactional
    fun updatePost(email: String, info: PostUpdateInfo): Post {
        val writer: User = userStore.getByEmail(email)

        val id: Long = info.id
        val post: Post = postStore.getById(id)

        if (post.writer.id != writer.id) {
            // TODO: 예외 처리
            throw RuntimeException("해당 게시글의 수정 권한이 없습니다.")
        }

        val type: PostType = info.type
        val category: Category = info.category
        val title: String = info.title
        val description: String = info.description
        val startDate: LocalDate = info.startDate
        val endDate: LocalDate = info.endDate
        val delivery: Boolean = info.delivery
        val place: String? = info.place
        val minPrice: Int = info.minPrice
        val maxPrice: Int = info.maxPrice
        // *** only for sale post ***
        val product: String? = info.product

        post.updatePost(category, title, description, delivery, place, minPrice, maxPrice, product)
        updateDates(post, startDate, endDate)

        return post
    }

    /* 게시글 삭제 */
    @Transactional
    fun deletePost(email: String, id: Long) {
        val writer: User = userStore.getByEmail(email)
        val post: Post = postStore.getById(id)

        if (post.writer.id != writer.id) {
            // TODO: 예외 처리
            throw RuntimeException("해당 게시글의 삭제 권한이 없습니다.")
        }

        postStore.deletePost(post)
    }
}