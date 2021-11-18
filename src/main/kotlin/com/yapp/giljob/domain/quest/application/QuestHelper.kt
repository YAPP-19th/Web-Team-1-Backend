package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class QuestHelper {
    companion object {
        fun getQuestById(questRepository: QuestRepository, questId: Long) = questRepository.findByIdOrNull(questId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        fun findQuestById(questRepository: QuestRepository, questId: Long) = questRepository.findByIdOrNull(questId)
    }
}