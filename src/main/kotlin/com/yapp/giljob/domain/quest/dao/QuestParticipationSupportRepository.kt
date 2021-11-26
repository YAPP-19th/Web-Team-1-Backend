package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.quest.vo.QuestSupportWithSubVo

interface QuestParticipationSupportRepository {
    fun countParticipants(): Long
    fun countQuests(): Long
    fun findByParticipantId(questId: Long?, participantId: Long, position: Position, size: Long): List<QuestSupportVo>
}