package com.yapp.giljob.domain.roadmap.domain

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class RoadmapParticipatedPK(
    var participantId: Long,
    var roadmapId: Long
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoadmapParticipatedPK

        if (participantId != other.participantId) return false
        if (roadmapId != other.roadmapId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = participantId.hashCode()
        result = 31 * result + roadmapId.hashCode()
        return result
    }
}
