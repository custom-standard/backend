package com.example.custard.domain.fixtures

import com.example.custard.domain.post.dto.info.PostCreateInfo
import com.example.custard.domain.post.dto.info.PostReadInfo
import com.example.custard.domain.post.dto.info.PostUpdateInfo
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.fixtures.DateFixtures.Companion as DateFixtures

class PostFixtures {
    companion object {
        const val deliverable = true
        const val undeliverable = false

        const val salePostTitle: String = "판매글 제목"
        const val salePostDescription: String = "판매글 설명"
        const val salePostPlace: String = "판매 장소"
        const val salePostMinPrice: Int = 50000
        const val salePostMaxPrice: Int = 100000
        const val salePostProduct: String = "판매 상품"

        const val modifiedSalePostTitle: String = "수정된 판매글 제목"
        const val modifiedSalePostDescription: String = "수정된 판매글 설명"
        const val modifiedSalePostPlace: String = "수정된 판매 장소"
        const val modifiedSalePostMinPrice: Int = 100000
        const val modifiedSalePostMaxPrice: Int = 200000

        const val purchasePostTitle: String = "구매글 제목"
        const val purchasePostDescription: String = "구매글 설명"
        const val purchasePostPlace: String = "구매 장소"
        const val purchasePostMinPrice: Int = 50000
        const val purchasePostMaxPrice: Int = 100000

        const val modifiedPurchasePostTitle: String = "수정된 구매글 제목"
        const val modifiedPurchasePostDescription: String = "수정된 구매글 설명"
        const val modifiedPurchasePostPlace: String = "수정된 구매 장소"
        const val modifiedPurchasePostMinPrice: Int = 100000
        const val modifiedPurchasePostMaxPrice: Int = 200000

        val salePostCreateInfo: PostCreateInfo = PostCreateInfo(1L, salePostTitle, salePostDescription, DateFixtures.dateInfos, deliverable, salePostPlace, salePostMinPrice, salePostMaxPrice, 1L)
        val salePost: Post = Post(UserFixtures.seller, CategoryFixtures.foodCategory, salePostTitle, salePostDescription, deliverable, salePostPlace, salePostMinPrice, salePostMaxPrice, salePostProduct)

        val salePostUpdateInfo: PostUpdateInfo = PostUpdateInfo(1L, 2L, modifiedSalePostTitle, modifiedSalePostDescription, DateFixtures.dateInfos, deliverable, modifiedSalePostPlace, modifiedSalePostMinPrice, modifiedSalePostMaxPrice)
        val modifiedSalePost: Post = Post(UserFixtures.seller, CategoryFixtures.generalCategory, modifiedSalePostTitle, modifiedSalePostDescription, deliverable, modifiedSalePostPlace, modifiedSalePostMinPrice, modifiedSalePostMaxPrice, salePostProduct)

        val purchasePostCreateInfo: PostCreateInfo = PostCreateInfo(1L, purchasePostTitle, purchasePostDescription, DateFixtures.dateInfos, deliverable, purchasePostPlace, purchasePostMinPrice, purchasePostMaxPrice)
        val purchasePost: Post = Post(UserFixtures.buyer, CategoryFixtures.foodCategory, purchasePostTitle, purchasePostDescription, deliverable, purchasePostPlace, purchasePostMinPrice, purchasePostMaxPrice)

        val purchasePostUpdateInfo: PostUpdateInfo = PostUpdateInfo(1L, 2L, modifiedPurchasePostTitle, modifiedPurchasePostDescription, DateFixtures.dateInfos, deliverable, modifiedPurchasePostPlace, modifiedPurchasePostMinPrice, modifiedPurchasePostMaxPrice)
        val modifiedPurchasePost: Post = Post(UserFixtures.buyer, CategoryFixtures.generalCategory, modifiedPurchasePostTitle, modifiedPurchasePostDescription, deliverable, modifiedPurchasePostPlace, modifiedPurchasePostMinPrice, modifiedPurchasePostMaxPrice)

        val salePostReadInfoNull: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, null, null, null, null)
        val purchasePostReadInfoNull: PostReadInfo = PostReadInfo(0, 10, PostType.PURCHASE, null, null, null, null)

        val salePostReadInfoOfFood: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, 1L, null, null, null)
        val salePostReadInfoOfGeneral: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, 2L, null, null, null)

        val salePostReadInfoAt2024_01_01: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, null, DateFixtures.date_2024_01_01, null, null)
        val salePostReadInfoAt2024_01_03: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, null, DateFixtures.date_2024_01_03, null, null)

        val salePostReadInfo_belowMinRange: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, null, null, 30000, 50000)
        val salePostReadInfo_aboveMaxRange: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, null, null, 100000, 200000)
        val salePostReadInfo_withinRange: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, null, null, 60000, 80000)
        val salePostReadInfo_overlappingRange: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, null, null, 80000, 120000)
        val salePostReadInfo_outOfRange: PostReadInfo = PostReadInfo(0, 10, PostType.SALE, null, null, 120000, 200000)
    }
}