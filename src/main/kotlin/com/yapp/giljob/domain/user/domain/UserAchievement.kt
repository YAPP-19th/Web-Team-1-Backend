package com.yapp.giljob.domain.user.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import javax.persistence.*

@Table(name = "user_achievement")
@Entity
class UserAchievement(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_achievement_id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user:User,

    @ManyToOne
    @JoinColumn(name = "achievement_id")
    val achievement: Achievement,

    @Column(name = "is_representative", nullable = false)
    var isRepresentative: Boolean = false
) {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(UserAchievement::id)
    }
}