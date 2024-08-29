package com.example.custard.domain.order.model

import com.example.custard.domain.common.file.File
import jakarta.persistence.*

@Entity
class OrderImage (
    order: Order,
    file: File,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val order: Order = order

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "file_id")
    val file: File = file
}