package io.codero.portionservice.service

import io.codero.portionservice.dto.PortionDto
import io.codero.portionservice.util.PortionUtil.getLogger
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PortionProducerService(val kafkaTemplate: KafkaTemplate<String, PortionDto>) {
    fun send(dto: PortionDto) {
        kafkaTemplate.send("portions", dto)
        getLogger().info("#### <- {}", dto)
    }
}
