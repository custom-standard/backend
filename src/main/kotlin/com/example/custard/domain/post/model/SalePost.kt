package com.example.custard.domain.post.model

import com.example.custard.domain.user.model.User
import jakarta.persistence.Entity
import java.util.Date

@Entity
class SalePost (
    writer: User,
    category: String,
    title: String,
    description: String,
    // TODO: 날짜 엔티티 추가 후 변경
    dates: String,
    delivery: Boolean,
    place: String?,

    // TODO: 상품 엔티티 추가 후 변경
    product: String,
) : Post(writer, category, title, description, dates, delivery, place) {
    // TODO: 상품 엔티티 추가 후 변경
    var product: String = product

    fun updateProduct(product: String) {
        this.product = product
    }
}