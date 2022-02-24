package io.codero.portionservice.service.impl

import io.codero.portionservice.entity.Portion
import io.codero.portionservice.repository.PortionRepository
import io.codero.portionservice.service.PortionService
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class PortionServiceImpl(private val portionRepository: PortionRepository) : PortionService {
    override fun add(portion: Portion): UUID {
        return portionRepository.save(portion).id
    }

    override fun getById(id: UUID): Portion {
        return portionRepository.findById(id).orElse(null)
    }
}
