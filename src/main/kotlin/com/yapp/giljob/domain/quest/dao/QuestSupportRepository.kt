package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.dto.QuestConditionDto
import com.yapp.giljob.domain.quest.vo.QuestPositionCountVo
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.quest.vo.QuestListVo
import org.springframework.data.domain.Pageable

interface QuestSupportRepository {
    fun countParticipantsByQuestId(questId: Long): Long
    fun findByQuestId(questId: Long): QuestSupportVo?
    fun getQuestList(conditionDto: QuestConditionDto, pageable: Pageable): QuestListVo
    fun getQuestPositionCount(): List<QuestPositionCountVo>
}