package io.codero.portionservice.service

import io.codero.portionservice.dto.CreatePortionDto
import io.codero.portionservice.facade.PortionFacade
import io.codero.portionservice.util.LoggerDelegate
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PortionListener(private val portionFacade: PortionFacade) {
    private val logger by LoggerDelegate()

    @Value("\${spring.kafka.consumer.topic}")
    private val topic: String? = null

    @KafkaListener(topics = ["\${spring.kafka.consumer.topic}"], containerFactory = "kafkaListenerContainerFactory")
    fun getPortion(dto: CreatePortionDto) {
        logger.info("$topic: #### -> $dto")
        portionFacade.add(dto)
    }
}
