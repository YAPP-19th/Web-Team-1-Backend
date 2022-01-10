package com.yapp.giljob.domain.quest.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*

@Table(
    name = "quest_participation",
    indexes = [Index(name = "i_quest_id_and_review", columnList = "quest_id, review")]
)
@Entity
class QuestParticipation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_participation_id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "quest_id")
    val quest: Quest,

    @ManyToOne
    @JoinColumn(name = "participant_id")
    val participant: User,

    @Column(name = "is_completed", nullable = false)
    var isCompleted: Boolean = false,

    @Column(name = "review", nullable = true)
    var review: String? = null,

    @Column(name = "review_created_at", nullable = true)
    var reviewCreatedAt: LocalDateTime? = null
) : BaseEntity() {
    fun complete() {
        this.isCompleted = true
    }

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(QuestParticipation::id)
    }
}