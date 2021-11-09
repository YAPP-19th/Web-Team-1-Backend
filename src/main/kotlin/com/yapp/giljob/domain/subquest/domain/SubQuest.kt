package com.yapp.giljob.domain.subquest.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.global.common.domain.BaseEntity
import javax.persistence.*

@Table(name = "sub_quest")
@Entity
class SubQuest (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_quest_id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "quest_id")
    var quest: Quest? = null,

    @Column(nullable = false)
    var name: String
) : BaseEntity() {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(SubQuest::id)
    }
}