package io.codero.portionservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.UUID

data class CreatePortionDto(
    @JsonProperty("letterIds")
    var letterIds: List<UUID>,

    @JsonProperty("localDateTime")
    var localDateTime: LocalDateTime
)
