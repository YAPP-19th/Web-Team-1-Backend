package com.yapp.giljob.domain.subquest.dao

import com.yapp.giljob.domain.subquest.vo.SubQuestCompletedCountVo

interface SubQuestParticipationSupportRepository {
    fun countSubQuestCompletedByParticipantId(participantId: Long): List<SubQuestCompletedCountVo>
}