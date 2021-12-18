package com.yapp.giljob.domain.roadmap.domain

import com.yapp.giljob.domain.quest.domain.Quest
import javax.persistence.*

@Table(name = "roadmap_quest")
@Entity
class RoadmapQuest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_quest_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    val roadmap: Roadmap,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    val quest: Quest
)