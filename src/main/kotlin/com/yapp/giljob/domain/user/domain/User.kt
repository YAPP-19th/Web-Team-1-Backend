package com.yapp.giljob.domain.user.domain

import com.yapp.giljob.domain.position.domain.Position
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
)