package com.yapp.giljob.domain.subquest.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.user.domain.User
import javax.persistence.*

@Table(name = "sub_quest_participation")
@Entity
class SubQuestParticipation(
    @EmbeddedId
    val id: SubQuestParticipationPK,

    @MapsId("subQuestId")
    @ManyToOne
    @JoinColumn(name = "sub_quest_id")
    val subQuest: SubQuest,

    @MapsId("participantId")
    @ManyToOne
    @JoinColumn(name = "participant_id")
    val participant: User,

    @ManyToOne
    @JoinColumn(name = "quest_id")
    val quest: Quest,

    @Column(name = "is_completed", nullable = false)
    var isCompleted: Boolean = true
) {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(SubQuestParticipation::id)
    }
}