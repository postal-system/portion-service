package io.codero.portionservice.service

import io.codero.portionservice.dto.PortionDto
import io.codero.portionservice.util.LoggerDelegate
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PortionProducerService(val kafkaTemplate: KafkaTemplate<String?, PortionDto>) {
    private val logger by LoggerDelegate()

    @Value("\${spring.kafka.producer.topic}")
    private val topic: String = ""

    fun send(dto: PortionDto) {
        kafkaTemplate.send(topic, dto)
        logger.info("$topic: #### <- $dto")
    }
}
