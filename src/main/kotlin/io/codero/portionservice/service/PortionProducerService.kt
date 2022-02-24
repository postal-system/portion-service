package io.codero.portionservice.service

import io.codero.portionservice.dto.PortionDto
import io.codero.portionservice.util.LoggerDelegate
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PortionProducerService(val kafkaTemplate: KafkaTemplate<String, PortionDto>) {
    private val logger by LoggerDelegate()

    fun send(dto: PortionDto) {
        kafkaTemplate.send("portions", dto)
        logger.info("#### <- {}", dto)
    }
}
