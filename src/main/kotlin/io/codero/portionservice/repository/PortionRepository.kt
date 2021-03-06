package io.codero.portionservice.repository

import io.codero.portionservice.entity.Portion
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface PortionRepository : JpaRepository<Portion, UUID>
