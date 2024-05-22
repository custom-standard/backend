package com.example.custard.domain.post.service

import com.example.custard.config.auth.oauth2.AuthProvider
import com.example.custard.domain.post.PostFactory
import com.example.custard.domain.post.dto.info.*
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.post.model.date.Date
import com.example.custard.domain.post.model.date.PostDate
import com.example.custard.domain.post.repository.PostCustomRepository
import com.example.custard.domain.post.repository.PostRepository
import com.example.custard.domain.post.repository.PostStoreImpl
import com.example.custard.domain.post.repository.category.CategoryRepository
import com.example.custard.domain.post.repository.category.CategoryStoreImpl
import com.example.custard.domain.post.repository.date.DateRepository
import com.example.custard.domain.post.repository.date.DateStoreImpl
import com.example.custard.domain.post.repository.date.PostDateRepository
import com.example.custard.domain.post.repository.date.PostDateStoreImpl
import com.example.custard.domain.post.service.category.CategoryStore
import com.example.custard.domain.post.service.date.DateStore
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

// FunSpec : 필드 변수 사용이 불가능, 함수 테스트에 주로 사용
// BehaviorSpec : BDD 스타일, Given, When, Then 구문 사용
// AnotationSpec: Junit과 가장 비슷, kotest로 마이그레이션 할 시 편리하게 사용 가능

/*
stub: 응답값을 설정하는 것
 */

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

    val postFactory: PostFactory = PostFactory()

    val user = User(AuthProvider.KAKAO, "test@test.com", "test", "test")
    val otherUser = User(AuthProvider.KAKAO, "testother@test.com", "testother", "testother")

    val category = Category("TEST CATEGORY", "test category")

    /* data */
    val expectedPurchasePost: Post = postFactory.createPost(PostType.PURCHASE, user, category, "title", "description", true, null, 30000, 50000, null)
    val expectedUpdatedPurchasePost: Post = postFactory.createPost(PostType.PURCHASE, user, category, "updated title", "updated description", true, null, 40000, 60000, null)

    val expectedSalePost: Post = postFactory.createPost(PostType.SALE, user, category, "title", "description", true, null, 30000, 50000, "temporary product")
    val expectedUpdatedSalePost: Post = postFactory.createPost(PostType.SALE, user, category, "updated title", "updated description", true, null, 40000, 60000, "updated temporary product")

    val expectedDate: Date = Date(LocalDate.of(2024, 4, 1), null)
    val updatedDate: Date = Date(LocalDate.of(2024, 5, 1), null)

    val purchasePosts: List<Post> = postFactory.createPurchasePosts(3)
    val salePosts: List<Post> = postFactory.createSalePosts(3)

    every { userRepository.findByUuid(user.uuid) } returns user
    every { categoryRepository.findById(0) } returns Optional.of(category)

    every { postDateRepository.deleteAll(any<List<PostDate>>()) } returns Unit
    every { postDateRepository.existsPostDateByDate(any<Date>())} returns false

    every { dateRepository.existsDateByDateAndTime(any(), any()) } returns false
    every { dateRepository.save(any<Date>()) } returns expectedDate
    every { dateRepository.delete(any<Date>()) } returns Unit

    beforeEach {
        every { userRepository.save(user) } returns user
        every { userRepository.save(otherUser) } returns otherUser

        userRepository.save(user)
        userRepository.save(otherUser)

        every { categoryRepository.save(category) } returns category
        categoryRepository.save(category)
    }

    /* PURCHASE */

    /*
    구매 게시글을 생성한다.
    [ ] 구매 게시글을 생성한다.
    // TODO: 에러 발생
     */

    Given("구매 게시글을 생성할 때") {
        When("유효한 정보로 구매 게시글을 생성하면") {
            val dates: List<DateInfo> = listOf(
                DateInfo(LocalDate.of(2024, 4, 1), null),
            )

            val info: PostCreateInfo = postFactory.createPostCreateInfo(
                PostType.PURCHASE,
                category.id,
                dates,
                true,
                30000,
                50000
            )

            every { postRepository.save(any<Post>()) } returns expectedPurchasePost
            every { postDateRepository.saveAll(any<List<PostDate>>()) } returns listOf(PostDate(expectedPurchasePost, expectedDate))

            // TODO: Dates 처리

            val result = postService.createPost(user.uuid.toString(), info)
            Then("생성한 구매 게시글이 반환된다") {
                assertEquals(expectedPurchasePost.type, result.type)
                assertEquals(expectedPurchasePost.category.name, result.category.name)
                assertEquals(expectedPurchasePost.title, result.title)
                assertEquals(expectedPurchasePost.description, result.description)
                assertEquals(expectedPurchasePost.dates.size, result.dates.size)
            }
        }
    }

    /*
    구매 게시글을 조회한다.
    [ ] 필터 조건이 없을 경우 모든 구매 게시글을 조회한다.
    [ ] 필터 조건에 따른 구매 게시글을 조회한다.
    // TODO: 에러 발생
     */

    Given("모든 구매 게시글을 조회할 때") {
        When("필터 조건이 존재하지 않는 경우") {
            val info = postFactory.createPostReadInfo(
                PostType.PURCHASE,
                null, null, null, null
            )
            every { postCustomRepository.findAllPurchasePost(null, null, null, null) } returns purchasePosts
            val result = postService.getPosts(user.uuid.toString(), info)

            Then("모든 구매 게시글이 조회된다") {
                assertEquals(3, result.size)
            }
        }

        When("필터 조건이 존재할 경우") {
            /* 카테고리, 시작 날짜, 끝 날짜, 최소 가격, 최대 가격 */
            val info = postFactory.createPostReadInfo(
                PostType.PURCHASE,
                null, null, 8000, 12000
            )
            every { postCustomRepository.findAllPurchasePost(null, null, 8000, 12000) } returns purchasePosts.filter { it.minPrice <= 12000 && it.maxPrice >= 8000 }
            val result = postService.getPosts(user.uuid.toString(), info)

            Then("필터 조건에 따른 구매 게시글이 조회된다") {
                assertEquals(1, result.size)
                assertEquals(10000, result[0].minPrice)
                assertEquals(20000, result[0].maxPrice)
            }
        }
    }

    // TODO: 게시글 단건 조회

    Given("구매 게시글을 수정할 때") {
        When("유효한 정보로 구매 게시글을 수정하면") {
            val dates: List<DateInfo> = listOf(
                DateInfo(LocalDate.of(2024, 5, 1), null),
            )

            val info: PostUpdateInfo = postFactory.createPostUpdateInfo(
                PostType.PURCHASE,
                1L,
                category.id,
                dates,
                true,
                40000,
                60000
            )

            every { postRepository.findById(1L) } returns Optional.of(expectedUpdatedPurchasePost)
            every { postDateRepository.findAllByPost(any<Post>()) } returns listOf(PostDate(expectedUpdatedPurchasePost, expectedDate))

            val result = postService.updatePost(user.uuid.toString(), info)
            Then("수정한 구매 게시글이 반환된다") {
                assertEquals(expectedUpdatedPurchasePost.type, result.type)
                assertEquals(expectedUpdatedPurchasePost.category.name, result.category.name)
                assertEquals(expectedUpdatedPurchasePost.title, result.title)
                assertEquals(expectedUpdatedPurchasePost.description, result.description)
                assertEquals(expectedUpdatedPurchasePost.dates.size, result.dates.size)
            }
        }
    }

    Given("구매 게시글을 삭제할 때") {
        When("유효한 정보로 구매 게시글을 삭제하면") {
            every { postRepository.delete(any<Post>()) } returns Unit
            val result = postService.deletePost(user.uuid.toString(), 1L)

            Then("삭제 성공에 대한 결과가 반환된다") {
                assertEquals(Unit, result)
                // TODO: 삭제 성공에 대한 결과가 반환되어야 함
            }
        }
    }

    /* SALE */

    /*
    판매 게시글을 생성한다.
    [ ] 판매 게시글을 생성한다.
    // TODO: 에러 발생
     */

    Given("판매 게시글을 생성할 때") {
        When("유효한 정보로 판매 게시글을 생성하면") {
            val dates: List<DateInfo> = listOf(
                DateInfo(LocalDate.of(2024, 4, 1), null),
            )
            val info: PostCreateInfo = postFactory.createPostCreateInfo(
                PostType.SALE,
                category.id,
                dates,
                true,
                30000,
                50000
            )

            every { postRepository.save(any<Post>()) } returns expectedSalePost
            every { postDateRepository.saveAll(any<List<PostDate>>()) } returns listOf(PostDate(expectedSalePost, expectedDate))

            val result = postService.createPost(user.uuid.toString(), info)
            Then("생성한 판매 게시글이 반환된다") {
                assertEquals(expectedSalePost.type, result.type)
                assertEquals(expectedSalePost.category.name, result.category.name)
                assertEquals(expectedSalePost.title, result.title)
                assertEquals(expectedSalePost.description, result.description)
                assertEquals(expectedSalePost.dates.size, result.dates.size)
            }
        }
    }

    /*
    판매 게시글을 조회한다.
    [ ] 판매 게시글을 조회한다.
    [ ] 필터 조건에 따른 판매 게시글을 조회한다.
    // todo: 에러 발생
     */

    Given("모든 판매 게시글을 조회할 때") {
        When("필터 조건이 존재하지 않는 경우") {
            val info = postFactory.createPostReadInfo(
                PostType.SALE,
                null, null, null, null
            )
            every { postCustomRepository.findAllSalePost(null, null, null, null) } returns salePosts
            val result = postService.getPosts(user.uuid.toString(), info)

            Then("모든 판매 게시글이 조회된다") {
                assertEquals(3, result.size)
            }
        }

        When("필터 조건이 존재할 경우") {
            /* 카테고리, 시작 날짜, 끝 날짜, 최소 가격, 최대 가격 */
            val info = postFactory.createPostReadInfo(
                PostType.SALE,
                null, null, 8000, 12000
            )
            every { postCustomRepository.findAllSalePost(null, null, 8000, 12000) } returns salePosts.filter { it.minPrice <= 12000 && it.maxPrice >= 8000 }
            val result = postService.getPosts(user.uuid.toString(), info)

            Then("필터 조건에 따른 판매 게시글이 조회된다") {
                assertEquals(1, result.size)
                assertEquals(10000, result[0].minPrice)
                assertEquals(20000, result[0].maxPrice)
            }
        }
    }

    // TODO: 게시글 단건 조회

    /*
    판매 게시글을 수정한다.
    [ ] 판매 게시글을 수정한다.
    // todo: 에러 발생
     */

    Given("판매 게시글을 수정할 때") {
        When("유효한 정보로 판매 게시글을 수정하면") {
            val dates: List<DateInfo> = listOf(
                DateInfo(LocalDate.of(2024, 5, 1), null),
                DateInfo(LocalDate.of(2024, 5, 2), null),
                DateInfo(LocalDate.of(2024, 5, 3), null),
                DateInfo(LocalDate.of(2024, 5, 4), null)
            )
            val info: PostUpdateInfo = postFactory.createPostUpdateInfo(
                PostType.SALE,
                2L,
                category.id,
                dates,
                true,
                40000,
                60000
            )

            every { postDateRepository.findAllByPost(any<Post>()) } returns listOf(PostDate(expectedUpdatedPurchasePost, expectedDate))
            every { postRepository.findById(2L) } returns Optional.of(expectedUpdatedSalePost)

            val result = postService.updatePost(user.uuid.toString(), info)
            Then("수정한 판매 게시글이 반환된다") {
                assertEquals(expectedUpdatedSalePost.type, result.type)
                assertEquals(expectedUpdatedSalePost.category.name, result.category.name)
                assertEquals(expectedUpdatedSalePost.title, result.title)
                assertEquals(expectedUpdatedSalePost.description, result.description)
                assertEquals(expectedUpdatedSalePost.dates.size, result.dates.size)
            }
        }
    }

    /*
    판매 게시글을 삭제한다.
    [ ] 판매 게시글을 삭제한다.
    // todo: 에러 발생
     */

    Given("판매 게시글을 삭제할 때") {
        When("유효한 정보로 판매 게시글을 삭제하면") {
            every { postRepository.delete(any<Post>()) } returns Unit
            val result = postService.deletePost(user.uuid.toString(), 1L)

            Then("삭제 성공에 대한 결과가 반환된다") {
                assertEquals(Unit, result)
                // TODO: 삭제 성공에 대한 결과가 반환되어야 함
            }
        }
    }
}) {
}