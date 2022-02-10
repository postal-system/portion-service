package io.codero.portionservice.entity

import com.vladmihalcea.hibernate.type.array.ListArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


@Entity
@TypeDef(name = "list-array", typeClass = ListArrayType::class)
data class Portion(
    @Id
    @Column(name = "id", columnDefinition = "pg-uuid")
    val id: UUID,

    @Type(type = "list-array")
    @Column(name = "letter_ids", columnDefinition = "uuid[]")
    val letterIds: MutableList<UUID>,

    @Column(name = "sending_date", nullable = false)
    val localDateTime: LocalDateTime
)
