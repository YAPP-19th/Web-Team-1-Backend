package com.yapp.giljob.domain.subquest.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class SubQuestParticipatedPK(
    var participantId: Long,
    var subQuestId: Long
) : Serializable {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(SubQuestParticipatedPK::participantId, SubQuestParticipatedPK::subQuestId)
    }
}
