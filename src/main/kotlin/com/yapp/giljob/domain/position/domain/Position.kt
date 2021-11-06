package com.yapp.giljob.domain.position.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import javax.persistence.*

@Table(name = "position")
@Entity
class Position(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    val id: Long? = null,

    val name: String
) {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Position::id)
    }
}