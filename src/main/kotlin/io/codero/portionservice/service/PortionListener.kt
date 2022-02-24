package io.codero.portionservice.service

import io.codero.portionservice.dto.CreatePortionDto
import io.codero.portionservice.facade.PortionFacade
import io.codero.portionservice.util.LoggerDelegate
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PortionListener(private val portionFacade: PortionFacade) {
    private val logger by LoggerDelegate()

    @KafkaListener(topics = ["\${spring.kafka.consumer.portion-topic}"], containerFactory = "kafkaListenerContainerFactory")
    fun getPortion(dto: CreatePortionDto) {
        logger.info("#### -> {}", dto)
        portionFacade.add(dto)
    }
}
