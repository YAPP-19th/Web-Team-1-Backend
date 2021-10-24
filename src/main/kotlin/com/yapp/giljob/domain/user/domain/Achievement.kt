package com.yapp.giljob.domain.user.domain

import javax.persistence.*

@Table(name = "achievement")
@Entity
class Achievement(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ability_id")
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,
)