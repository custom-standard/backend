package com.example.custard.domain.post.model.date

import com.example.custard.domain.post.model.Post
import jakarta.persistence.*

@Entity
class PostDate (
    post: Post,
    date: Date,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post = post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date_id")
    val date: Date = date
}