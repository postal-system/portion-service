package io.codero.portionservice.service

import io.codero.portionservice.dto.CreatePortionDto
import io.codero.portionservice.facade.PortionFacade
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class PortionListenerService(private val portionFacade: PortionFacade) {
    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${spring.kafka.consumer.portion-topic}"])
    fun getPortion(dto: CreatePortionDto) {
        log.info("#### -> {}", dto)
        portionFacade.add(dto)
    }
}