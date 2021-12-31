package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.vo.QuestReviewVo
import com.yapp.giljob.domain.quest.vo.QuestSupportVo

interface QuestParticipationSupportRepository {
    fun countParticipants(): Long
    fun countQuests(): Long
    fun findByParticipantId(questId: Long?, participantId: Long, isCompleted: Boolean, size: Long): List<QuestSupportVo>
    fun getQuestReviewByQuestIdLessThanAndOrderByIdDesc(questId: Long, cursor: Long?, size: Long): List<QuestReviewVo>
}