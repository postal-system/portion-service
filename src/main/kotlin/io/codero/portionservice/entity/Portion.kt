package io.codero.portionservice.entity

import com.vladmihalcea.hibernate.type.array.ListArrayType
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef

@Entity
@TypeDef(name = "list-array", typeClass = ListArrayType::class)
data class Portion(
    @Id
    @Column(name = "id", columnDefinition = "pg-uuid")
    val id: UUID,

    @Type(type = "list-array")
    @Column(name = "letter_ids", columnDefinition = "uuid[]")
    val letterIds: List<UUID>,

    @Column(name = "sending_date", nullable = false)
    val timestamp: LocalDateTime
)
