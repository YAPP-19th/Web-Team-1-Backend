package com.yapp.giljob.domain.position.domain

import javax.persistence.*

@Table(name = "position")
@Entity
class Position(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    val id: Long? = null,

    val name: String
)