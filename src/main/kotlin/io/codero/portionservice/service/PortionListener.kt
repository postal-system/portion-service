package io.codero.portionservice.service

import io.codero.portionservice.dto.CreatePortionDto
import io.codero.portionservice.facade.PortionFacade
import io.codero.portionservice.util.PortionUtil.getLogger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PortionListener(private val portionFacade: PortionFacade) {

    @KafkaListener(topics = ["\${spring.kafka.consumer.portion-topic}"])
    fun getPortion(dto: CreatePortionDto) {
        getLogger().info("#### -> {}", dto)
        portionFacade.add(dto)
    }
}
