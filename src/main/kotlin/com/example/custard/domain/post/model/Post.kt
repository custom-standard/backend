package com.example.custard.domain.post.model

import com.example.custard.domain.user.model.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
// TODO : BaseEntity 추가
class Post (
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
        protected set

    var title: String = title
        protected set

    var description: String = description
        protected set

    // TODO: 날짜 엔티티 추가 후 변경
    var dates: String? = dates
        protected set

    var delivery: Boolean = delivery
        protected set

    var place: String? = place
        protected set

    var public: Boolean = false
        protected set
//    var deleted: Boolean = false

    fun updatePost(
        category: String,
        title: String,
        description: String,
        dates: String?,
        delivery: Boolean,
        place: String?,
    ) {
        this.category = category
        this.title = title
        this.description = description
        this.dates = dates
        this.delivery = delivery
        this.place = place
    }

    fun updatePublic(public: Boolean) {
        this.public = public
    }
}