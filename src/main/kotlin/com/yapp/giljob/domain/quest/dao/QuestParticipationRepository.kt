package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.domain.QuestParticipation
import org.springframework.data.jpa.repository.JpaRepository

interface QuestParticipationRepository : JpaRepository<QuestParticipation, Long>, QuestParticipationSupportRepository {
    fun findByQuestIdAndParticipantId(questId: Long, participantId: Long): QuestParticipation?
    fun countByParticipantIdAndIsCompletedTrue(participantId: Long): Long
    fun countByQuestId(questId: Long): Long
    fun existsByQuestIdAndParticipantId(questId: Long, participantId: Long): Boolean
}