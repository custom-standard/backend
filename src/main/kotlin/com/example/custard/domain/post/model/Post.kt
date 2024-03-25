package com.example.custard.domain.post.model

import com.example.custard.domain.post.model.date.PostDate
import com.example.custard.domain.user.model.User
import jakarta.persistence.*

@Entity
// TODO : BaseEntity 추가
class Post (
    writer: User,
    category: Category,
    title: String,
    description: String,
    dates: MutableList<PostDate>,
    delivery: Boolean,
    place: String?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    val writer: User = writer

    var category: Category = category
        protected set

    var title: String = title
        protected set

    var description: String = description
        protected set

    @OneToMany(mappedBy = "post")
    var dates: MutableList<PostDate> = dates
        protected set

    var delivery: Boolean = delivery
        protected set

    var place: String? = place
        protected set

    var public: Boolean = false
        protected set
//    var deleted: Boolean = false

    fun updatePost(
        category: Category,
        title: String,
        description: String,
        dates: MutableList<PostDate>,
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