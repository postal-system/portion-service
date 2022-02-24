package io.codero.portionservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.UUID

data class PortionDto(
    @JsonProperty("id")
    val id: UUID,
    @JsonProperty("letterIds")
    val letterIds: List<UUID>,
    @JsonProperty("localDateTime")
    val localDateTime: LocalDateTime
)
