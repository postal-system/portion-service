package io.codero.portionservice.controller

import io.codero.portionservice.dto.CreatePortionDto
import io.codero.portionservice.facade.PortionFacade
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/portions")
class PortionController(private val facade: PortionFacade) {
    @PostMapping
    fun post(@RequestBody dto: CreatePortionDto): UUID {
        return facade.add(dto)
    }
}