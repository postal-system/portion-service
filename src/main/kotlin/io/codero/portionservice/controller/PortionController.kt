package io.codero.portionservice.controller

import io.codero.portionservice.dto.PortionDto
import io.codero.portionservice.facade.PortionFacade
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/portions")
class PortionController(private val facade: PortionFacade) {
    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID): ResponseEntity<PortionDto>? {
        return ResponseEntity.ok().body(facade.getById(id))
    }
}
