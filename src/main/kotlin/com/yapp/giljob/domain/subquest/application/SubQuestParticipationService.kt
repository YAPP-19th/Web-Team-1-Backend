package com.yapp.giljob.domain.subquest.application

import com.yapp.giljob.domain.quest.dto.response.QuestDetailSubQuestResponseDto
import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import com.yapp.giljob.domain.subquest.dao.SubQuestRepository
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipation
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipationPK
import com.yapp.giljob.domain.subquest.dto.response.SubQuestProgressResponseDto
import com.yapp.giljob.domain.subquest.vo.SubQuestParticipationVo
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.global.util.SubQuestProgressCalculate.Companion.calculateProgress
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
        val subQuestParticipationVo = getSubQuestParticipationVo(subQuestId, user)
        val subQuest = subQuestParticipationVo.subQuest

        subQuestParticipationVo.subQuestParticipation?.let {
            if (!it.isCompleted) it.complete()
            else throw BusinessException(ErrorCode.ALREADY_COMPLETED_SUBQUEST)
        } ?: subQuestParticipationRepository.save(
            SubQuestParticipation(
                subQuestParticipationVo.subQuestParticipationPK,
                subQuest,
                user,
                subQuest.quest
            )
        )
    }

    @Transactional
    fun cancelSubQuest(subQuestId: Long, user: User) {
        val subQuestParticipation = getSubQuestParticipationVo(subQuestId, user).subQuestParticipation
        subQuestParticipation?.let {
            if (it.isCompleted) it.cancel()
            else throw BusinessException(ErrorCode.NOT_COMPLETED_SUBQUEST)
        } ?: throw BusinessException(ErrorCode.SUBQUEST_PARTICIPATION_NOT_FOUND)
    }

    private fun getSubQuestParticipationVo(subQuestId: Long, user: User): SubQuestParticipationVo {
        val subQuest =
            subQuestRepository.findByIdOrNull(subQuestId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        val subQuestParticipationPK = SubQuestParticipationPK(user.id!!, subQuestId)
        return SubQuestParticipationVo(
            subQuest,
            subQuestParticipationPK,
            subQuestParticipationRepository.findByIdOrNull(subQuestParticipationPK)
        )
    }

    fun getQuestDetailSubQuest(questId: Long, user: User): QuestDetailSubQuestResponseDto {
        val subQuestProgressList
                = subQuestParticipationRepository.getSubQuestProgressByQuestIdAndParticipantId(questId, user.id!!)

        if (subQuestProgressList.isEmpty()) return QuestDetailSubQuestResponseDto(
            progress = 0,
            subQuestProgressList = emptyList()
        )

        val totalSubQuestCount = subQuestProgressList.size
        val subQuestCompletedCount = subQuestProgressList.count{ it.isCompleted }.toLong()

        val progress = calculateProgress(totalSubQuestCount, subQuestCompletedCount)

        return QuestDetailSubQuestResponseDto(
            progress = progress,
            subQuestProgressList = subQuestProgressList.map {
                SubQuestProgressResponseDto(
                    subQuestId = it.subQuestId,
                    subQuestName = it.subQuestName,
                    isCompleted = it.isCompleted
                )
            }
        )
    }
}