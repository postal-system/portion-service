package io.codero.portionservice.service

import io.codero.portionservice.dto.PortionDto
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PortionProducerService(val kafkaTemplate: KafkaTemplate<String, PortionDto>) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun send(dto: PortionDto) {
        kafkaTemplate.send("portions", dto)
        log.info("#### <- {}", dto)
    }
}