package com.example.custard.domain.post.model

import com.example.custard.domain.post.model.date.PostDate
import com.example.custard.domain.user.model.User
import jakarta.persistence.*

// TODO : BaseEntity 추가
@Entity
class Post (
    type: PostType,
    writer: User,
    category: Category,
    title: String,
    description: String,
    dates: MutableList<PostDate>,
    delivery: Boolean,
    place: String?,
    minPrice: Int,
    maxPrice: Int,
    // TODO: 상품 엔티티 추가 후 변경
    product: String?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    val type: PostType = type

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

    var minPrice: Int = minPrice
        protected set

    var maxPrice: Int = maxPrice
        protected set

    // TODO: 상품 엔티티 추가 후 변경
    var product: String? = product
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
        minPrice: Int,
        maxPrice: Int,
        product: String?,
    ) {
        this.category = category
        this.title = title
        this.description = description
        this.dates = dates
        this.delivery = delivery
        this.place = place
        this.minPrice = minPrice
        this.maxPrice = maxPrice
        this.product = product
    }

    fun updatePublic(public: Boolean) {
        this.public = public
    }
}