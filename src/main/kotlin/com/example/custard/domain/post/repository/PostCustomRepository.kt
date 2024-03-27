package com.example.custard.domain.post.repository

import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import java.time.LocalDate

/*
- 카테고리에 따른 구매 게시글을 조회한다.
- 지역에 따른 구매 게시글을 조회한다.
- 특정 예약 날짜가 포함된 구매 게시글을 조회한다.
- 구매 희망 가격에 따른 구매 게시글을 조회한다.
*/

interface PostCustomRepository {
    // TODO: 지역 필터링 추가
    fun findAllPurchasePost(category: Category?, date: LocalDate?, minPrice: Int?, maxPrice: Int?): List<Post>
    fun findAllSalePost(category: Category?, date: LocalDate?, minPrice: Int?, maxPrice: Int?): List<Post>
}