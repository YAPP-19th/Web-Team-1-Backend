package com.yapp.giljob.domain.tag.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class QuestTagPK (
    var questId: Long?,
    var tagId: Long?
) : Serializable {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(QuestTagPK::questId, QuestTagPK::tagId)
    }
}