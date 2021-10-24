package com.yapp.giljob.domain.user.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.global.common.domain.BaseEntity
import javax.persistence.*

@Table(name = "user")
@Entity
class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null,

    @Column(name = "social_id", nullable = false)
    val socialId: String,

    @Column(nullable = false)
    var nickname: String,

    @ManyToOne
    @JoinColumn(name = "position_id")
    var position: Position
) : BaseEntity() {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(User::id)
    }
}