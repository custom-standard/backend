package com.example.custard.domain.post.repository

import com.example.custard.domain.post.exception.NoSuchPostException
import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.PostType
import com.example.custard.domain.post.service.PostStore
import com.example.custard.domain.user.model.User
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class PostStoreImpl (
    private val postRepository: PostRepository,
    private val postCustomRepository: PostCustomRepository,
) : PostStore {
    override fun savePost(post: Post): Post {
        return postRepository.save(post)
    }

    override fun getById(id: Long): Post {
        return postRepository.findById(id)
            .orElseThrow { NoSuchPostException("ID가 $id 인 게시글을 찾을 수 없습니다.")}
    }

    override fun findAllPurchasePost(
        category: Category?,
        date: LocalDate?,
        minPrice: Int?,
        maxPrice: Int?
    ): List<Post> {
        return postCustomRepository.findAllPurchasePost(category, date, minPrice, maxPrice)
    }

    override fun findAllSalePost(
        category: Category?,
        date: LocalDate?,
        minPrice: Int?,
        maxPrice: Int?
    ): List<Post> {
        return postCustomRepository.findAllSalePost(category, date, minPrice, maxPrice)
    }

    override fun findPurchasePostByWriter(writer: User): List<Post> {
        return postRepository.findByWriterAndType(writer, PostType.PURCHASE)
    }

    override fun findSalePostByWriter(writer: User): List<Post> {
        return postRepository.findByWriterAndType(writer, PostType.SALE)
    }

    override fun deletePost(post: Post) {
        postRepository.delete(post)
    }
}