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
    delivery: Boolean,
    place: String?,
    minPrice: Int,
    maxPrice: Int,
    // TODO: 상품 엔티티 추가 후 변경
    product: String?,
) {
    constructor(writer: User, category: Category, title: String, description: String, delivery: Boolean, place: String?, minPrice: Int, maxPrice: Int)
            : this(PostType.PURCHASE, writer, category, title, description, delivery, place, minPrice, maxPrice, null)

    constructor(writer: User, category: Category, title: String, description: String, delivery: Boolean, place: String?, minPrice: Int, maxPrice: Int, product: String?)
            : this(PostType.SALE, writer, category, title, description, delivery, place, minPrice, maxPrice, product)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    val type: PostType = type

    @ManyToOne(fetch = FetchType.LAZY)
    val writer: User = writer

    @ManyToOne(fetch = FetchType.LAZY)
    var category: Category = category
        protected set

    var title: String = title
        protected set

    var description: String = description
        protected set

    @OneToMany(mappedBy = "post")
    var dates: MutableList<PostDate> = mutableListOf()
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
        delivery: Boolean,
        place: String?,
        minPrice: Int,
        maxPrice: Int,
        product: String?,
    ) {
        this.category = category
        this.title = title
        this.description = description
        this.delivery = delivery
        this.place = place
        this.minPrice = minPrice
        this.maxPrice = maxPrice
        this.product = product
    }

    fun updateDates(dates: MutableList<PostDate>) {
        this.dates = dates
    }

    fun updatePublic(public: Boolean) {
        this.public = public
    }

    fun isWriter(user: User): Boolean {
        return writer == user
    }

    fun isSale(): Boolean {
        return type == PostType.SALE
    }
}