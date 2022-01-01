package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.vo.QuestPositionCountVo
import com.yapp.giljob.domain.quest.vo.QuestSupportVo

interface QuestSupportRepository {
    fun findByIdLessThanAndOrderByIdDesc(questId: Long?, position: Position, userId: Long? = null, size: Long): List<QuestSupportVo>
    fun countParticipantsByQuestId(questId: Long): Long

    fun search(keyword: String, position: Position, size: Long, questId: Long?): List<QuestSupportVo>

    fun findByQuestId(questId: Long): QuestSupportVo?

    fun getQuestPositionCount(): List<QuestPositionCountVo>
}