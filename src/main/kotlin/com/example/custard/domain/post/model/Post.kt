package com.example.custard.domain.post.model

import com.example.custard.domain.post.exception.PostForbiddenException
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

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var dates = mutableListOf<PostDate>()
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
    val product: String? = product

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
    ) {
        this.category = category
        this.title = title
        this.description = description
        this.delivery = delivery
        this.place = place
        this.minPrice = minPrice
        this.maxPrice = maxPrice
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

    fun validateWriter(user: User) {
        if (!isWriter(user)) {
            throw PostForbiddenException("해당 게시글의 작성자가 아닙니다.")
        }
    }
}