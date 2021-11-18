package com.yapp.giljob.domain.quest.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.BaseEntity
import javax.persistence.*

@Table(name = "quest_participated")
@Entity
class QuestParticipated(
    @EmbeddedId
    val id: QuestParticipatedPK,

    @MapsId("questId")
    @ManyToOne
    @JoinColumn(name = "quest_id")
    val quest: Quest,

    @MapsId("participantId")
    @ManyToOne
    @JoinColumn(name = "participant_id")
    val user: User,

    @Column(name = "is_completed", nullable = false)
    var isCompleted: Boolean = false
) : BaseEntity() {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(QuestParticipated::id)
    }
}