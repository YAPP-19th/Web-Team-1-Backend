package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestParticipatedRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.domain.QuestParticipated
import com.yapp.giljob.domain.quest.domain.QuestParticipatedPK
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestParticipationService(
    private val questRepository: QuestRepository,
    private val questParticipatedRepository: QuestParticipatedRepository
) {
    @Transactional
    fun participateQuest(questId: Long, user: User) {
        val quest = validateQuest(questId, user)

        val questParticipatedPK = QuestParticipatedPK(user.id!!, questId)
        if (questParticipatedRepository.existsById(questParticipatedPK)) throw BusinessException(ErrorCode.ALREADY_PARTICIPATED_QUEST)

        questParticipatedRepository.save(QuestParticipated(questParticipatedPK, quest, user))
    }

    private fun validateQuest(questId: Long, user: User): Quest {
        val quest = QuestHelper.getQuestById(questRepository, questId)
        if (quest.user == user) throw BusinessException(ErrorCode.CANNOT_PARTICIPATE_MY_QUEST)
        return quest
    }
}