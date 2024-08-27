package com.example.custard.domain.post.service

import com.example.custard.domain.common.file.File
import com.example.custard.domain.common.file.FileStore
import com.example.custard.domain.post.service.date.DateStore
import com.example.custard.domain.post.dto.info.*
import com.example.custard.domain.post.dto.response.PostDetailResponse
import com.example.custard.domain.post.dto.response.PostResponse
import com.example.custard.domain.post.model.*
import com.example.custard.domain.post.service.category.CategoryStore
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.service.UserStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@Service
class PostService(
    private val postStore: PostStore,
    private val dateStore: DateStore,
    private val userStore: UserStore,
    private val categoryStore: CategoryStore,
    private val fileStore: FileStore
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
    @Transactional
    fun createPost(userUUID: String, info: PostCreateInfo, files: List<MultipartFile>): PostDetailResponse {
        val writer: User = userStore.getByUUID(userUUID)
        val category: Category = categoryStore.getCategory(info.categoryId)

        val post: Post = PostCreateInfo.toEntity(info, category, writer)
        var savedPost: Post = postStore.savePost(post)

        val dates: List<Date> = dateStore.findOrCreateDate(info.dates)
        post.updateDates((dates.map { date -> PostDate(post, date) }).toMutableList())

        storeImages(post, files)

        savedPost = postStore.savePost(post)

        return PostDetailResponse.of(savedPost)
    }

    private fun updateDates(post: Post, dates: List<DateInfo>) {
        val dateEntities = dateStore.findOrCreateDate(dates)
        val postDates = dateEntities.map { PostDate(post, it) }

        post.dates.retainAll(postDates)
        post.dates.addAll(postDates)
    }

    private fun updateImages(post: Post, files: List<MultipartFile>) {
        fileStore.deleteFiles(post.images.map { it.file })
        storeImages(post, files)
    }

    /* 게시글 수정 */
    @Transactional
    fun updatePost(userUUID: String, info: PostUpdateInfo, files: List<MultipartFile>): PostDetailResponse {
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
        updateImages(post, files)

        post.updatePost(category, title, description, delivery, place, minPrice, maxPrice)

        return PostDetailResponse.of(post)
    }

    /* 게시글 삭제 */
    @Transactional
    fun deletePost(userUUID: String, id: Long) {
        val user: User = userStore.getByUUID(userUUID)
        val post: Post = postStore.getById(id)

        post.validateWriter(user)

        postStore.deletePost(post)
    }

    private fun storeImages(post: Post, files: List<MultipartFile>) {
        val imagePath: String = "post/${post.id}"
        val images: List<File> = fileStore.uploadFiles(imagePath, files)

        val postImages: List<PostImage> = images.map { PostImage(post, it) }

        post.images.retainAll(postImages)
        post.images.addAll(postImages)
    }
}