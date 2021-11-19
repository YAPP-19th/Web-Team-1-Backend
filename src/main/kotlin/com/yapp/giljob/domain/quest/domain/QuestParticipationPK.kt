package com.yapp.giljob.domain.quest.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class QuestParticipationPK(
    var participantId: Long,
    var questId: Long
) : Serializable {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties =
            arrayOf(QuestParticipationPK::participantId, QuestParticipationPK::questId)
    }
}