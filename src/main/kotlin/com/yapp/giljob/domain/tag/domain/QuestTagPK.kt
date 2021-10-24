package com.yapp.giljob.domain.tag.domain

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class QuestTagPK (
    var questId: Long,
    var tagId: Long
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestTagPK

        if (questId != other.questId) return false
        if (tagId != other.tagId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = questId.hashCode()
        result = 31 * result + tagId.hashCode()
        return result
    }
}