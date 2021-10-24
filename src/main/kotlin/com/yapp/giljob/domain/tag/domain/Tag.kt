package com.yapp.giljob.domain.tag.domain

import javax.persistence.*

@Table(name = "tag")
@Entity
class Tag(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    val id: Long? = null,

    @Column(nullable = false)
    var name: String
)