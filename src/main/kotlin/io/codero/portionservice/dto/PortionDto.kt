package io.codero.portionservice.dto

import java.time.LocalDateTime
import java.util.UUID

data class PortionDto(
    val id: UUID,
    val letterIds: List<UUID>,
    val localDateTime: LocalDateTime
)
