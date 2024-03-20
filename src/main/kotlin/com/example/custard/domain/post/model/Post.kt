package com.example.custard.domain.post.model

import com.example.custard.domain.user.model.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
// TODO : BaseEntity 추가
abstract class Post (
    writer: User,
    // TODO: 카테고리 엔티티 추가 후 변경
    category: String,
    title: String,
    description: String,
    // TODO: 날짜 엔티티 추가 후 변경
    dates: String?,
    delivery: Boolean,
    place: String?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne
    val writer: User = writer

    // TODO: 카테고리 엔티티 추가 후 변경
    var category: String = category

    var title: String = title

    var dates: String? = dates

    var delivery: Boolean = delivery

    var place: String? = place

    var description: String = description

    var public: Boolean = false
//    var deleted: Boolean = false

    fun updateCategory(category: String) {
        this.category = category
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateDates(dates: String) {
        this.dates = dates
    }

    fun updateDelivery(delivery: Boolean) {
        this.delivery = delivery
    }

    fun updatePlace(place: String) {
        this.place = place
    }

    fun updateDescription(description: String) {
        this.description = description
    }

    fun updatePublic(public: Boolean) {
        this.public = public
    }
}