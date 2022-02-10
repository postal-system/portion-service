package io.codero.portionservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.*

data class CreatePortionDto(
    @JsonProperty("letterIds")
    val letterIds: MutableList<UUID>,

    @JsonProperty("localDateTime")
    val  localDateTime: LocalDateTime
)

