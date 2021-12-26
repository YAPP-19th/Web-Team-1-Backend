package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.quest.domain.QuestParticipationPK
import com.yapp.giljob.domain.quest.dto.request.QuestReviewCreateRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestCountResponseDto
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.domain.Ability
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class QuestParticipationService(
    private val questRepository: QuestRepository,
    private val questParticipationRepository: QuestParticipationRepository,
    private val abilityRepository: AbilityRepository,

    private val subQuestService: SubQuestService
) {
    @Transactional
    fun participateQuest(questId: Long, user: User) {
        val quest = QuestHelper.getQuestById(questRepository, questId)

        val questParticipationPK = QuestParticipationPK(user.id!!, questId)
        if (questParticipationRepository.existsById(questParticipationPK)) throw BusinessException(ErrorCode.ALREADY_PARTICIPATED_QUEST)

        questParticipationRepository.save(QuestParticipation(questParticipationPK, quest, user))
    }

    @Transactional(readOnly = true)
    fun getAllQuestCount() = QuestCountResponseDto(
        totalQuestCount = QuestHelper.totalCount(questRepository),
        onProgressQuestCount = getOnProgressQuestCount(),
        totalParticipantCount = getQuestParticipantCount()
    )


    @Transactional
    fun completeQuest(questId: Long, user: User) {
        val questParticipation = questParticipationRepository.findByIdOrNull(QuestParticipationPK(user.id!!, questId))
            ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        validateCompletedQuest(questParticipation, questId, user.id!!)
        questParticipation.isCompleted = true

        val quest = questParticipation.quest
        val ability = abilityRepository.findByUserIdAndPosition(user.id!!, quest.position!!) ?: abilityRepository.save(Ability(user = user, position = quest.position!!))
        ability.point += quest.difficulty!! * 100L
    }

    private fun validateCompletedQuest(questParticipation: QuestParticipation, questId: Long, userId: Long) {
        if (questParticipation.isCompleted) {
            throw BusinessException(ErrorCode.ALREADY_COMPLETED_QUEST)
        }
        val completedSubQuestCount = subQuestService.countCompletedSubQuest(questId, userId)
        if (questParticipation.quest.subQuestList.size != completedSubQuestCount) {
            throw BusinessException(ErrorCode.NOT_COMPLETED_QUEST)
        }
    }

    private fun getOnProgressQuestCount() = questParticipationRepository.countQuests()

    private fun getQuestParticipantCount() = questParticipationRepository.countParticipants()

    @Transactional
    fun createQuestReview(
        questId: Long,
        questReviewCreateRequestDto: QuestReviewCreateRequestDto,
        user: User
    ) {
        var questParticipation: QuestParticipation =
            questParticipationRepository.getQuestParticipationByQuestIdAndParticipantId(questId, user.id!!)
                ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)

        if (!questParticipation.isCompleted) {
            throw BusinessException(ErrorCode.CAN_NOT_CREATE_QUEST_REVIEW)
        }

        questParticipation.review = questReviewCreateRequestDto.review
        questParticipation.reviewCreatedAt = LocalDateTime.now()
    }

    fun getQuestParticipationStatus(questId: Long, userId: Long): String {
        val questParticipation
                = questParticipationRepository.getQuestParticipationByQuestIdAndParticipantId(questId, userId)
            ?: return "아직 참여하지 않은 퀘스트입니다."

        return when(questParticipation.isCompleted) {
            false -> "참여중인 퀘스트입니다."
            true -> "완료한 퀘스트입니다."
        }
    }
}