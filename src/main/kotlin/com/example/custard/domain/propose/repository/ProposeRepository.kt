package com.example.custard.domain.propose.repository

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.propose.model.Propose
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProposeRepository : JpaRepository<Propose, Long> {
    fun findByOrder(order: Order): List<Propose>
}