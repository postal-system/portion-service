package io.codero.portionservice.dto

import java.time.LocalDateTime
import java.util.*

data class PortionDto(
    val id: UUID,
    val letterIds: MutableList<UUID>,
    val localDateTime: LocalDateTime
)
