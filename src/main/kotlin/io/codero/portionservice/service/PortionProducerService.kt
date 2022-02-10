package io.codero.portionservice.service

import io.codero.portionservice.dto.PortionDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PortionProducerService(val kafkaTemplate: KafkaTemplate<String, PortionDto>) {
    private val log = LoggerFactory.getLogger(javaClass)

//    @Value("\${spring.kafka.producer.portion-topic}")
//    private val topic: String? = null

    fun send(dto: PortionDto) {
//        if (topic != null) {
            kafkaTemplate.send("portions", dto)
            log.info("#### <- {}", dto)
//        }
    }
}