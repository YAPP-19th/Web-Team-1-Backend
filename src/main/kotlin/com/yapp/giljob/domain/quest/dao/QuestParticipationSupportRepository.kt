package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.vo.QuestListVo
import com.yapp.giljob.domain.quest.vo.QuestReviewVo
import org.springframework.data.domain.Pageable

interface QuestParticipationSupportRepository {
    fun countParticipants(): Long
    fun countQuests(): Long
    fun findByParticipantId(participantId: Long, isCompleted: Boolean, pageable: Pageable): QuestListVo
    fun getQuestReviewList(questId: Long, pageable: Pageable): List<QuestReviewVo>
}