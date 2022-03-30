package io.codero.portionservice.facade

import io.codero.portionservice.dto.CreatePortionDto
import io.codero.portionservice.dto.PortionDto
import io.codero.portionservice.entity.Portion
import io.codero.portionservice.service.PortionProducerService
import io.codero.portionservice.service.PortionService
import java.util.UUID
import javax.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class PortionFacade(
    private val portionService: PortionService,
    private val portionProducerService: PortionProducerService,
) {
    @Transactional
    fun add(dto: CreatePortionDto): UUID {
        val uuid: UUID = UUID.randomUUID()
        val portion = Portion(uuid, dto.letterIds, dto.timestamp)
        portionService.add(portion)
        val portionDto = PortionDto(portion.id, portion.letterIds, portion.timestamp)
        portionProducerService.send(portionDto)
        return uuid
    }

    fun getById(id: UUID): PortionDto {
        val portion = portionService.getById(id)
        return PortionDto(portion.id, portion.letterIds, portion.timestamp)
    }
}
