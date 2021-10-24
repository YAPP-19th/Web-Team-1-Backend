package com.yapp.giljob.domain.quest.domain

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class QuestParticipatedPK(
    var participantId: Long,
    var questId: Long
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestParticipatedPK

        if (participantId != other.participantId) return false
        if (questId != other.questId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = participantId.hashCode()
        result = 31 * result + questId.hashCode()
        return result
    }
}