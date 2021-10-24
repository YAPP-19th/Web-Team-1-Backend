package com.yapp.giljob.domain.subquest.domain

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class SubQuestParticipatedPK(
    var participantId: Long,
    var subQuestId: Long
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubQuestParticipatedPK

        if (participantId != other.participantId) return false
        if (subQuestId != other.subQuestId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = participantId.hashCode()
        result = 31 * result + subQuestId.hashCode()
        return result
    }
}
