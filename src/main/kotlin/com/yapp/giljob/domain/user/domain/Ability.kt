package com.yapp.giljob.domain.user.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.position.domain.Position
import javax.persistence.*

@Table(name = "ability")
@Entity
class Ability(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ability_id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    val position: Position,

    @Column(nullable = false)
    var point: Long = 0L
) {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Ability::id)
    }
}