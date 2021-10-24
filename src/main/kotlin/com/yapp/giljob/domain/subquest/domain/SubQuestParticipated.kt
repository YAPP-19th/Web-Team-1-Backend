package com.yapp.giljob.domain.subquest.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.user.domain.User
import javax.persistence.*

@Table(name = "sub_quest_participated")
@Entity
class SubQuestParticipated(
    @EmbeddedId
    val id: SubQuestParticipatedPK,

    @MapsId("subQuestId")
    @ManyToOne
    @JoinColumn(name = "sub_quest_id")
    val subQuest: SubQuest,

    @MapsId("participantId")
    @ManyToOne
    @JoinColumn(name = "participant_id")
    val user: User,

    @Column(name = "is_completed", nullable = false)
    var isCompleted: Boolean = false
) {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(SubQuestParticipated::id)
    }
}