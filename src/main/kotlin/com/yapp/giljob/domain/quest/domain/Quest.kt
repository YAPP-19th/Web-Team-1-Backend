package com.yapp.giljob.domain.quest.domain

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.user.domain.User
import javax.persistence.*

@Table(name = "quest")
@Entity
class Quest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "register_user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "position_id")
    var position: Position,

    @Column(nullable = false)
    var isRealQuest: Boolean,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var difficulty: Int,

    @Column(nullable = false)
    var thumbnail: String,

    @Column(nullable = false)
    var attachment: String,

    @Column(nullable = false)
    var detail: String
)