package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.quest.domain.QuestParticipationPK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuestParticipationRepository: JpaRepository<QuestParticipation, QuestParticipationPK> {
    @Query("select count(distinct id.participantId) from QuestParticipation")
    fun countByParticipant(): Long

    @Query("select count(distinct id.questId) from QuestParticipation")
    fun countByQuest(): Long
}