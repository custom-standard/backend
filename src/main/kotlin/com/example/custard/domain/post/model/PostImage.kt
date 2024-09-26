package com.example.custard.domain.post.model

import com.example.custard.domain.common.file.File
import jakarta.persistence.*

@Entity
class PostImage (
    post: Post,
    file: File,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post = post

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    val file: File = file
}