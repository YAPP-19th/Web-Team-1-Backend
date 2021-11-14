package com.yapp.giljob.domain.tag.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.quest.domain.Quest
import javax.persistence.*

@Table(name = "quest_tag")
@Entity
class QuestTag(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "quest_id")
    val quest: Quest,

    @ManyToOne
    @JoinColumn(name = "tag_id")
    val tag: Tag
) {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(QuestTag::id)
    }
}