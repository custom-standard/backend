package com.example.custard.domain.post.model

import com.example.custard.domain.user.model.User

class PurchasePost (
    writer: User,
    category: String,
    title: String,
    description: String,
    // TODO: 날짜 엔티티 추가 후 변경
    dates: String,
    delivery: Boolean,
    place: String?,

    filePath: String,
) : Post(writer, category, title, description, dates, delivery, place) {
    val filePath: String = filePath
}