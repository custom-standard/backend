package com.example.custard.domain.post.service

import com.example.custard.domain.post.PostFactory
import com.example.custard.domain.post.dto.info.*
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.post.repository.PostCustomRepository
import com.example.custard.domain.post.repository.PostRepository
import com.example.custard.domain.post.repository.PostStoreImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

// FunSpec : 필드 변수 사용이 불가능, 함수 테스트에 주로 사용
// BehaviorSpec : BDD 스타일, Given, When, Then 구문 사용
// AnotationSpec: Junit과 가장 비슷, kotest로 마이그레이션 할 시 편리하게 사용 가능

class PostServiceTest : BehaviorSpec({
    val postRepository: PostRepository = mockk();
    val postCustomRepository: PostCustomRepository = mockk();

    val postStore: PostStore = PostStoreImpl(postRepository, postCustomRepository)
    val postService: PostService = PostService(postStore)

    val postFactory: PostFactory = PostFactory()

    /* PURCHASE */

    /*
    구매 게시글을 생성한다.
    [ ] 구매 게시글을 생성한다.
    // TODO: 에러 발생
     */

    Given("구매 게시글을 생성할 때") {
        When("유효한 정보로 구매 게시글을 생성하면") {
            val info: PostCreateInfo = postFactory.createPostCreateInfo(
                PostType.PURCHASE,
                Category.FOOD,
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 4, 3),
                true,
                30000,
                50000
            )

            val result: Post = postService.createPost(info)
            Then("생성한 구매 게시글이 반환된다") {
                assertEquals(PostType.PURCHASE, result.type)
                assertEquals(Category.FOOD, result.category)
                assertEquals("title", result.title)
                assertEquals("description", result.description)
                assertEquals(true, result.public)
                assertEquals(3, result.dates.size)
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

            val result = postService.getPosts(info)
            Then("모든 구매 게시글이 조회된다") {
                assertEquals(3, result.size)
            }
        }

        When("필터 조건이 존재할 경우") {
            /* 카테고리, 시작 날짜, 끝 날짜, 최소 가격, 최대 가격 */
            val info = postFactory.createPostReadInfo(
                PostType.PURCHASE,
                Category.FOOD,
                null, null, null
            )

            val result = postService.getPosts(info)

            Then("필터 조건에 따른 구매 게시글이 조회된다") {
                assertEquals(1, result.size)
            }
        }
    }

    // TODO: 게시글 단건 조회

    Given("구매 게시글을 수정할 때") {
        When("유효한 정보로 구매 게시글을 수정하면") {
            val info: PostUpdateInfo = postFactory.createPostUpdateInfo(
                PostType.PURCHASE,
                1L,
                Category.FOOD,
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 4),
                true,
                40000,
                60000
            )

            val result = postService.updatePost(info)
            Then("수정한 구매 게시글이 반환된다") {
                assertEquals(PostType.PURCHASE, result.type)
                assertEquals(Category.FOOD, result.category)
                assertEquals("updated title", result.title)
                assertEquals("updated description", result.description)
                assertEquals(true, result.public)
                assertEquals(4, result.dates.size)
            }
        }
    }

    Given("구매 게시글을 삭제할 때") {
        When("유효한 정보로 구매 게시글을 삭제하면") {
            val result = postService.deletePost(1L)
            Then("삭제 성공에 대한 결과가 반환된다") {
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
            val info: PostCreateInfo = postFactory.createPostCreateInfo(
                PostType.SALE,
                Category.FOOD,
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 4, 3),
                true,
                30000,
                50000
            )

            val result = postService.createPost(info)
            Then("생성한 판매 게시글이 반환된다") {
                assertEquals(PostType.SALE, result.type)
                assertEquals(Category.FOOD, result.category)
                assertEquals("title", result.title)
                assertEquals("description", result.description)
                assertEquals(true, result.public)
                assertEquals(3, result.dates.size)
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

            val result = postService.getPosts(info)
            Then("모든 판매 게시글이 조회된다") {
                assertEquals(3, result.size)
            }
        }

        When("필터 조건이 존재할 경우") {
            /* 카테고리, 시작 날짜, 끝 날짜, 최소 가격, 최대 가격 */
            val info = postFactory.createPostReadInfo(
                PostType.SALE,
                Category.FOOD,
                null, null, null
            )

            val result = postService.getPosts(info)

            Then("필터 조건에 따른 판매 게시글이 조회된다") {
                assertEquals(1, result.size)
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
            val info: PostUpdateInfo = postFactory.createPostUpdateInfo(
                PostType.SALE,
                1L,
                Category.FOOD,
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 4),
                true,
                40000,
                60000
            )

            val result = postService.updatePost(info)
            Then("수정한 판매 게시글이 반환된다") {
                assertEquals(PostType.SALE, result.type)
                assertEquals(Category.FOOD, result.category)
                assertEquals("updated title", result.title)
                assertEquals("updated description", result.description)
                assertEquals(true, result.public)
                assertEquals(4, result.dates.size)
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
            val result = postService.deletePost(1L)
            Then("삭제 성공에 대한 결과가 반환된다") {
                // TODO: 삭제 성공에 대한 결과가 반환되어야 함
            }
        }
    }
}) {
}