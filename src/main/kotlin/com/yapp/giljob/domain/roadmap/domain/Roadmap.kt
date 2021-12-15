package com.yapp.giljob.domain.roadmap.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.BaseEntity
import javax.persistence.*

@Table(name = "roadmap")
@Entity
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

    @OneToMany
    var questList: MutableList<Quest> = mutableListOf()
) : BaseEntity() {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Roadmap::id)
    }
}