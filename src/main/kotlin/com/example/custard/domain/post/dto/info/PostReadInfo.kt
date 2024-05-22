package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.PostType
import java.time.LocalDate

class PostReadInfo (
    val page: Int,
    val size: Int,
    val type: PostType,
    val categoryId: Long?,
    val date: LocalDate?,
    val minPrice: Int?,
    val maxPrice: Int?
) {
}