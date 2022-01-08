package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.vo.QuestReviewVo
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import org.springframework.data.domain.Pageable

interface QuestParticipationSupportRepository {
    fun countParticipants(): Long
    fun countQuests(): Long
    fun findByParticipantId(participantId: Long, isCompleted: Boolean, pageable: Pageable): List<QuestSupportVo>
    fun getQuestReviewByQuestIdLessThanAndOrderByIdDesc(questId: Long, cursor: Long?, size: Long): List<QuestReviewVo>
}