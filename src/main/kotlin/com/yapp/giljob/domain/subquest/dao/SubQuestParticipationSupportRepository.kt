package com.yapp.giljob.domain.subquest.dao

import com.yapp.giljob.domain.subquest.vo.SubQuestCompletedCountVo
import com.yapp.giljob.domain.subquest.vo.SubQuestProgressVo

interface SubQuestParticipationSupportRepository {
    fun countSubQuestCompletedByParticipantId(participantId: Long): List<SubQuestCompletedCountVo>

    fun getSubQuestProgressByQuestIdAndParticipantId (questId: Long, participantId: Long): List<SubQuestProgressVo>
}