package com.yapp.giljob.domain.tag.domain

import com.yapp.giljob.domain.quest.domain.Quest
import javax.persistence.*

@Table(name = "quest_tag")
@Entity
class QuestTag(
    @EmbeddedId
    val id: QuestTagPK,

    @MapsId("questId")
    @ManyToOne
    @JoinColumn(name = "quest_id")
    val quest: Quest,

    @MapsId("tagId")
    @ManyToOne
    @JoinColumn(name = "tag_id")
    val tag: Tag
)