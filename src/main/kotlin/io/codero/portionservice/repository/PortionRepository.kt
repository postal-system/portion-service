package io.codero.portionservice.repository

import io.codero.portionservice.entity.Portion
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PortionRepository: JpaRepository<Portion, UUID>{
}