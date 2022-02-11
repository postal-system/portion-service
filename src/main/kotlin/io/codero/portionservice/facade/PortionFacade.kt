package io.codero.portionservice.facade

import io.codero.portionservice.dto.CreatePortionDto
import io.codero.portionservice.dto.PortionDto
import io.codero.portionservice.entity.Portion
import io.codero.portionservice.service.PortionProducerService
import io.codero.portionservice.service.PortionService
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

@Component
class PortionFacade(
    private val portionService: PortionService,
    private val portionProducerService: PortionProducerService,
) {
    @Transactional
    fun add(dto: CreatePortionDto): UUID {
        val uuid: UUID = UUID.randomUUID()
        val portion = Portion(uuid, dto.letterIds, dto.localDateTime)
        portionService.add(portion)
        val portionDto = PortionDto(portion.id, portion.letterIds, portion.localDateTime)
        portionProducerService.send(portionDto)
        return uuid
    }
}