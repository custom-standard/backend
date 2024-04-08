package com.example.custard.domain.post.dto.info

import com.example.custard.domain.post.model.Category
import com.example.custard.domain.post.model.PostType
import java.time.LocalDate

class PostReadInfo (
    val type: PostType,
    val category: Category?,
    val date: LocalDate?,
    val minPrice: Int?,
    val maxPrice: Int?
) {
}