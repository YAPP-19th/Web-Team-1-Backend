package com.yapp.giljob.domain.user.domain

import javax.persistence.*

@Table(name = "user_achievement")
@Entity
class UserAchievement(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_achievement_id")
    val id: Long? = null, // 꼭 필요할까요?

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user:User,

    @ManyToOne
    @JoinColumn(name = "achievement_id")
    val achievement: Achievement,

    @Column(name = "is_representative", nullable = false)
    var isRepresentative: Boolean = false
)