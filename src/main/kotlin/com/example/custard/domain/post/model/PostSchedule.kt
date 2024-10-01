package com.example.custard.domain.post.model

import jakarta.persistence.*

@Entity
class PostSchedule (
    post: Post,
    schedule: Schedule,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post = post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    val schedule: Schedule = schedule
}