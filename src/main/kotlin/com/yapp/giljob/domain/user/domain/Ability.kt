package com.yapp.giljob.domain.user.domain

import com.yapp.giljob.domain.position.domain.Position
import javax.persistence.*

@Table(name = "ability")
@Entity
class Ability(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ability_id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "register_user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "position_id")
    val position: Position,

    @Column(nullable = false)
    var value: Int
)