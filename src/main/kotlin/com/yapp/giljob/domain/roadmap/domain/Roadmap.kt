package com.yapp.giljob.domain.roadmap.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.roadmap.dto.request.RoadmapSaveRequestDto
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.BaseEntity
import javax.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Table(name = "roadmap")
@Entity
@SQLDelete(sql = "UPDATE roadmap SET deleted = true WHERE roadmap_id=?")
@Where(clause = "deleted = false")
class Roadmap(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "writer_id")
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    val position: Position,

    @Column(nullable = false)
    var name: String,

    @OneToMany(mappedBy = "roadmap")
    var questList: MutableList<RoadmapQuest> = mutableListOf(),

    @Column(nullable = false)
    var deleted: Boolean = false
) : BaseEntity() {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        fun of(roadmapSaveRequestDto: RoadmapSaveRequestDto, user: User): Roadmap {
            return Roadmap(
                user = user,
                position = roadmapSaveRequestDto.position,
                name = roadmapSaveRequestDto.name
            )
        }

        private val equalsAndHashCodeProperties = arrayOf(Roadmap::id)
    }
}