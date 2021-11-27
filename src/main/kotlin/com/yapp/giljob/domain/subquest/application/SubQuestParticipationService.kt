package com.yapp.giljob.domain.subquest.application

import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import com.yapp.giljob.domain.subquest.dao.SubQuestRepository
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipated
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipatedPK
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubQuestParticipationService(
    private val subQuestRepository: SubQuestRepository,
    private val subQuestParticipationRepository: SubQuestParticipationRepository
) {
    @Transactional
    fun completeSubQuest(subQuestId: Long, user: User) {
        val subQuest =
            subQuestRepository.findByIdOrNull(subQuestId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)

        val subQuestParticipationPK = SubQuestParticipatedPK(user.id!!, subQuestId)
        subQuestParticipationRepository.findByIdOrNull(subQuestParticipationPK)?.let {
            if (!it.isCompleted) it.isCompleted = true
            else throw BusinessException(ErrorCode.ALREADY_COMPLETED_SUBQUEST)
        } ?: subQuestParticipationRepository.save(SubQuestParticipated(subQuestParticipationPK, subQuest, user))
    }
}