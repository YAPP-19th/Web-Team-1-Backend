package com.yapp.giljob.domain.roadmap.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.user.domain.User
import javax.persistence.*

@Table(name = "roadmap_participated")
@Entity
class RoadmapParticipated(
    @EmbeddedId
    val id: RoadmapParticipatedPK,

    @MapsId("roadmapId")
    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    val roadmap: Roadmap,

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
        private val equalsAndHashCodeProperties = arrayOf(RoadmapParticipated::id)
    }
}