package com.example.custard.domain.post.service

import com.example.custard.domain.common.date.Date
import com.example.custard.domain.common.date.DateRepository
import com.example.custard.domain.common.date.DateStore
import com.example.custard.domain.common.date.DateStoreImpl
import com.example.custard.domain.fixtures.*
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostDate
import com.example.custard.domain.post.repository.PostCustomRepository
import com.example.custard.domain.post.repository.PostRepository
import com.example.custard.domain.post.repository.PostStoreImpl
import com.example.custard.domain.post.repository.category.CategoryRepository
import com.example.custard.domain.post.repository.category.CategoryStoreImpl
import com.example.custard.domain.post.repository.date.PostDateRepository
import com.example.custard.domain.post.repository.date.PostDateStoreImpl
import com.example.custard.domain.post.service.category.CategoryStore
import com.example.custard.domain.post.service.date.PostDateStore
import com.example.custard.domain.user.model.User
import com.example.custard.domain.user.repository.UserRepository
import com.example.custard.domain.user.repository.UserStoreImpl
import com.example.custard.domain.user.service.UserStore
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.util.Optional

class PostServiceTest : BehaviorSpec({
    val postRepository: PostRepository = mockk<PostRepository>()
    val postCustomRepository: PostCustomRepository = mockk<PostCustomRepository>()
    val postDateRepository: PostDateRepository = mockk<PostDateRepository>()
    val dateRepository: DateRepository = mockk<DateRepository>()
    val userRepository: UserRepository = mockk<UserRepository>()
    val categoryRepository: CategoryRepository = mockk<CategoryRepository>()

    val postStore: PostStore = PostStoreImpl(postRepository, postCustomRepository)
    val dateStore: DateStore = DateStoreImpl(dateRepository)
    val postDateStore: PostDateStore = PostDateStoreImpl(postDateRepository, dateStore)
    val categoryStore: CategoryStore = CategoryStoreImpl(categoryRepository)

    val userStore: UserStore = UserStoreImpl(userRepository)
    val postService: PostService = PostService(postStore, postDateStore, userStore, categoryStore)

    every { userRepository.findByUuid(UserFixtures.testUser.uuid) } returns UserFixtures.testUser
    every { userRepository.findByUuid(UserFixtures.seller.uuid) } returns UserFixtures.seller
    every { userRepository.findByUuid(UserFixtures.buyer.uuid) } returns UserFixtures.buyer

    every { categoryRepository.findById(1L) } returns Optional.of(CategoryFixtures.foodCategory)
    every { categoryRepository.findById(2L) } returns Optional.of(CategoryFixtures.generalCategory)
    every { categoryRepository.findById(3L) } returns Optional.of(CategoryFixtures.electronicCategory)

    beforeEach {
        every { userRepository.saveAll(any<List<User>>()) } returns listOf(UserFixtures.testUser, UserFixtures.seller, UserFixtures.buyer)
        userRepository.saveAll(listOf(UserFixtures.testUser, UserFixtures.seller, UserFixtures.buyer))

        every { categoryRepository.saveAll(any<List<Category>>()) } returns listOf(CategoryFixtures.foodCategory, CategoryFixtures.generalCategory, CategoryFixtures.electronicCategory)
        categoryRepository.saveAll(listOf(CategoryFixtures.foodCategory, CategoryFixtures.generalCategory, CategoryFixtures.electronicCategory))
    }

    /* PURCHASE */

    /*
    게시글을 생성한다.
    [ ] 판매/구매 게시글을 생성한다.
    // TODO: 에러 발생
     */

    Given("게시글을 생성할 때") {
        When("유효한 정보로 게시글을 생성하면") {
            every { postRepository.save(any<Post>()) } returns PostFixtures.salePost
            every { dateRepository.saveAll(any<List<Date>>()) } returns DateFixtures.dateEntities
            every { postDateRepository.saveAll(any<List<PostDate>>()) } returns PostDateFixtures.postDates
            every { dateRepository.existsDateByDateAndTime(any< LocalDate>(), any()) } returns false
            every { dateRepository.save(any<Date>()) } returnsMany DateFixtures.dateEntities

            val result = postService.createPost(UserFixtures.buyer.uuid.toString(), PostFixtures.salePostCreateInfo)
            val expectedPost = PostFixtures.salePost

            Then("생성된 게시글이 반환된다") {
                assertEquals(expectedPost.type, result.type)
                assertEquals(expectedPost.category.name, result.category.name)
                assertEquals(expectedPost.title, result.title)
                assertEquals(expectedPost.description, result.description)
                assertEquals(expectedPost.place, result.place)
                assertEquals(expectedPost.minPrice, result.minPrice)
                assertEquals(expectedPost.maxPrice, result.maxPrice)
                assertEquals(expectedPost.dates.size, result.dates.size)
                assertEquals(expectedPost.product, result.productId)
            }
        }
    }

    /*
    게시글을 조회한다.
    [ ] 게시글 타입(판매/구매)에 따른 게시글을 조회한다.
    [ ] 필터 조건(카테고리/날짜/가격)에 따른 구매 게시글을 조회한다.
    // TODO: 에러 발생
     */

    Given("모든 게시글을 조회할 때") {
        When("게시글 타입 조건이 존재하는 경우") {
            every { postCustomRepository.findAllSalePost(null, null, null, null) } returns listOf(PostFixtures.salePost)

            val result = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfoNull)

            Then("모든 구매 게시글이 조회된다") {
                assertEquals(1, result.size)
            }
        }

        When("카테고리 필터 조건이 존재할 경우") {
            every { postCustomRepository.findAllSalePost(CategoryFixtures.foodCategory, null, null, null) } returns listOf(PostFixtures.salePost)
            every { postCustomRepository.findAllSalePost(CategoryFixtures.generalCategory, null, null, null) } returns listOf()

            val resultWithData = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfoOfFood)
            val resultWithoutData = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfoOfGeneral)

            Then("카테고리 필터 조건에 따른 구매 게시글이 조회된다") {
                assertEquals(1, resultWithData.size)
                assertEquals(0, resultWithoutData.size)
            }
        }

        When("날짜 필터 조건이 존재할 경우") {
            every { postCustomRepository.findAllSalePost(null, DateFixtures.date_2024_01_01, null, null) } returns listOf(PostFixtures.salePost)
            every { postCustomRepository.findAllSalePost(null, DateFixtures.date_2024_01_03, null, null) } returns listOf()

            val resultWithData = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfoAt2024_01_01)
            val resultWithoutData = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfoAt2024_01_03)

            Then("날짜 필터 조건에 따른 구매 게시글이 조회된다.") {
                assertEquals(1, resultWithData.size)
                assertEquals(0, resultWithoutData.size)
            }
        }

        When("가격 필터 조건이 존재할 경우") {
            every { postCustomRepository.findAllSalePost(null, null, 30000, 50000) } returns listOf(PostFixtures.salePost)
            every { postCustomRepository.findAllSalePost(null, null, 100000, 200000) } returns listOf(PostFixtures.salePost)
            every { postCustomRepository.findAllSalePost(null, null, 60000, 80000) } returns listOf(PostFixtures.salePost)
            every { postCustomRepository.findAllSalePost(null, null, 80000, 120000) } returns listOf(PostFixtures.salePost)
            every { postCustomRepository.findAllSalePost(null, null, 120000, 200000) } returns listOf()

            val resultOfbelowMinRange = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfo_belowMinRange)
            val resultOfaboveMaxRange = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfo_aboveMaxRange)
            val resultOfwithinRange = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfo_withinRange)
            val resultOfoverlappingRange = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfo_overlappingRange)
            val resultOfoutOfRange = postService.getPosts(UserFixtures.testUser.uuid.toString(), PostFixtures.salePostReadInfo_outOfRange)

            Then("가격 필터 조건에 따른 구매 게시글이 조회된다.") {
                assertEquals(1, resultOfbelowMinRange.size)
                assertEquals(1, resultOfaboveMaxRange.size)
                assertEquals(1, resultOfwithinRange.size)
                assertEquals(1, resultOfoverlappingRange.size)
                assertEquals(0, resultOfoutOfRange.size)
            }
        }
    }

    // TODO: 게시글 단건 조회

    Given("게시글을 수정할 때") {
        When("유효한 정보로 게시글을 수정하면") {
            every { postRepository.findById(1L) } returns Optional.of(PostFixtures.salePost)
            every { postDateRepository.findAllByPost(PostFixtures.salePost) } returns PostDateFixtures.postDates
            every { postDateRepository.deleteAll(any<List<PostDate>>()) } returns Unit
            every { postDateRepository.existsPostDateByDate(any<Date>()) } returns false
            every { dateRepository.delete(any<Date>()) } returns Unit

            val result = postService.updatePost(UserFixtures.seller.uuid.toString(), PostFixtures.salePostUpdateInfo)
            val expectedPost = PostFixtures.modifiedSalePost

            Then("수정한 게시글이 반환된다") {
                assertEquals(expectedPost.type, result.type)
                assertEquals(expectedPost.category.name, result.category.name)
                assertEquals(expectedPost.title, result.title)
                assertEquals(expectedPost.description, result.description)
                assertEquals(expectedPost.place, result.place)
                assertEquals(expectedPost.minPrice, result.minPrice)
                assertEquals(expectedPost.maxPrice, result.maxPrice)
                assertEquals(expectedPost.dates.size, result.dates.size)
                assertEquals(expectedPost.product, result.productId)
            }
        }
    }

    Given("게시글을 삭제할 때") {
        When("유효한 정보로 게시글을 삭제하면") {
            every { postRepository.delete(PostFixtures.salePost) } returns Unit
            every { postDateRepository.deleteAll(any<List<PostDate>>()) } returns Unit
            every { postDateRepository.existsPostDateByDate(any<Date>()) } returns false
            every { dateRepository.delete(any<Date>()) } returns Unit

            val result = postService.deletePost(UserFixtures.seller.uuid.toString(), 1L)

            Then("삭제 성공에 대한 결과가 반환된다") {
                assertEquals(Unit, result)
                // TODO: 삭제 성공에 대한 결과가 반환되어야 함
            }
        }
    }
})
