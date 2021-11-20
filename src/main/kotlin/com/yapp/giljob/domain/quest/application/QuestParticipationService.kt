package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.quest.domain.QuestParticipationPK
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestParticipationService(
    private val questRepository: QuestRepository,
    private val questParticipationRepository: QuestParticipationRepository
) {
    @Transactional
    fun participateQuest(questId: Long, user: User) {
        val quest = validateAndGetQuest(questId, user)

        val questParticipationPK = QuestParticipationPK(user.id!!, questId)
        if (questParticipationRepository.existsById(questParticipationPK)) throw BusinessException(ErrorCode.ALREADY_PARTICIPATED_QUEST)

        questParticipationRepository.save(QuestParticipation(questParticipationPK, quest, user))
    }

    private fun validateAndGetQuest(questId: Long, user: User): Quest {
        val quest = QuestHelper.getQuestById(questRepository, questId)
        if (quest.user == user) throw BusinessException(ErrorCode.CANNOT_PARTICIPATE_MY_QUEST)
        return quest
    }
}