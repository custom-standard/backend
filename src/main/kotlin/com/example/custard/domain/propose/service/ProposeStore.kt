package com.example.custard.domain.propose.service

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.propose.model.Propose

interface ProposeStore {
    fun getById(proposeId: Long): Propose

    fun getByOrder(order: Order): List<Propose>

    fun savePropose(propose: Propose): Propose

    fun deletePropose(propose: Propose)
}