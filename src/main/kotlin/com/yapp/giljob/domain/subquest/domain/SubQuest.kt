package com.yapp.giljob.domain.subquest.domain

import com.yapp.giljob.domain.quest.domain.Quest
import javax.persistence.*

@Table(name = "sub_quest")
@Entity
class SubQuest (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_quest_id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "quest_id")
    val quest: Quest,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var order: Int
)