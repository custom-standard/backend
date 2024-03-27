package com.example.custard.domain.post.repository

import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.post.model.QPost
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class PostCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : PostCustomRepository {
    // TODO: 지역 필터링 추가
    override fun findAllPurchasePost(
        category: Category?,
        date: LocalDate?,
        minPrice: Int?,
        maxPrice: Int?
    ): List<Post> {
        val qPost = QPost.post

        return queryFactory
            .selectFrom(qPost)
            .where(
                qPost.type.eq(PostType.PURCHASE),
                eqCategory(category),
                eqDate(date)
            )
            .having(
                eqPrice(minPrice, maxPrice)
            )
            .fetch()
    }

    override fun findAllSalePost(
        category: Category?,
        date: LocalDate?,
        minPrice: Int?,
        maxPrice: Int?
    ): List<Post> {
        val qPost = QPost.post

        return queryFactory
            .selectFrom(qPost)
            .where(
                qPost.type.eq(PostType.SALE),
                eqCategory(category),
                eqDate(date)
            )
            .having(
                eqPrice(minPrice, maxPrice)
            )
            .fetch()
    }

    private fun eqCategory(category: Category?): BooleanExpression? {
        return category?.let { QPost.post.category.eq(category) }
    }

    // TODO: 지역 필터링 추가
    private fun eqPlace(place: String?): BooleanExpression? {
        return null
    }

    private fun eqDate(date: LocalDate?): BooleanExpression? {
        return date?.let {
            val post = QPost.post
            return post.dates.any().date.date.eq(date)
        }
    }

    private fun eqPrice(minPrice: Int?, maxPrice: Int?): BooleanExpression? {
        return when {
            minPrice != null && maxPrice != null ->
                QPost.post.minPrice.loe(maxPrice)
                .and(QPost.post.maxPrice.goe(minPrice))
            minPrice != null -> QPost.post.maxPrice.loe(minPrice)
            maxPrice != null -> QPost.post.minPrice.goe(maxPrice)
            else -> null
        }
    }
}