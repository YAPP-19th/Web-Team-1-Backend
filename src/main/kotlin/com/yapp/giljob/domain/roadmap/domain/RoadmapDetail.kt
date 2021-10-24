package com.yapp.giljob.domain.roadmap.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.quest.domain.Quest
import javax.persistence.*

@Table(name = "roadmap_quest")
@Entity
class RoadmapDetail(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_quest_id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "quest_id")
    val quest: Quest,

    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    val roadmap: Roadmap,

    @Column(nullable = false)
    var order: Int
) {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(RoadmapDetail::id)
    }
}