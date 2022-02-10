package io.codero.portionservice.service

import io.codero.portionservice.entity.Portion
import java.util.*

interface PortionService {
    fun add(portion: Portion):UUID
}