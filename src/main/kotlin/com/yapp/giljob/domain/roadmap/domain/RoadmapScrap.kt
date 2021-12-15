package com.yapp.giljob.domain.roadmap.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.user.domain.User
import javax.persistence.*

@Table(name = "roadmap_scrap")
@Entity
class RoadmapScrap(
    @EmbeddedId
    val id: RoadmapScrapPK,

    @MapsId("roadmapId")
    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    val roadmap: Roadmap,

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
) {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(RoadmapScrap::id)
    }
}