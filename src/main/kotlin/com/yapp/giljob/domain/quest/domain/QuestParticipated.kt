package com.yapp.giljob.domain.quest.domain

import com.yapp.giljob.domain.user.domain.User
import javax.persistence.*

@Table(name = "quest_participated")
@Entity
class QuestParticipated(
    @EmbeddedId
    val id: QuestParticipatedPK,

    @MapsId("questId")
    @ManyToOne
    @JoinColumn(name = "quest_id")
    val quest: Quest,

    @MapsId("participantId")
    @ManyToOne
    @JoinColumn(name = "participant_id")
    val user: User,

    @Column(name = "is_completed", nullable = false)
    var isCompleted: Boolean = false
)