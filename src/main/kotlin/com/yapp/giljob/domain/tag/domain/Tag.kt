package com.yapp.giljob.domain.tag.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import javax.persistence.*

@Table(name = "tag")
@Entity
class Tag(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    val id: Long? = null,

    @Column(nullable = false)
    var name: String
) {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Tag::id)
    }
}