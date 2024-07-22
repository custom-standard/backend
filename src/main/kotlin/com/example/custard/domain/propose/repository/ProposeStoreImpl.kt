package com.example.custard.domain.propose.repository

import com.example.custard.domain.order.model.Order
import com.example.custard.domain.propose.exception.NoSuchProposeException
import com.example.custard.domain.propose.model.Propose
import com.example.custard.domain.propose.service.ProposeStore
import org.springframework.stereotype.Component

@Component
class ProposeStoreImpl (
    private val proposeRepository: ProposeRepository,
) : ProposeStore {
    override fun getById(proposeId: Long): Propose {
        return proposeRepository.findById(proposeId)
            .orElseThrow { NoSuchProposeException("존재하지 않는 제안입니다.") }
    }

    override fun getByOrder(order: Order): List<Propose> {
        return proposeRepository.findByOrder(order)
    }

    override fun savePropose(propose: Propose): Propose {
        return proposeRepository.save(propose)
    }

    override fun deletePropose(propose: Propose) {
        proposeRepository.delete(propose)
    }
}