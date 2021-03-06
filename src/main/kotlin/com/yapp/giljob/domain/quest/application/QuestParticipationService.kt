package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.quest.dto.request.QuestReviewCreateRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestCountResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestReviewResponseDto
import com.yapp.giljob.domain.quest.vo.QuestReviewVo
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipationPK
import com.yapp.giljob.domain.subquest.vo.SubQuestParticipationVo
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.domain.Ability
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.dto.ListResponseDto
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.global.util.calculator.PointCalculator
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class QuestParticipationService(
    private val questRepository: QuestRepository,
    private val questParticipationRepository: QuestParticipationRepository,
    private val abilityRepository: AbilityRepository,

    private val subQuestService: SubQuestService,

    private val userMapper: UserMapper
) {
    @Transactional
    fun participateQuest(questId: Long, user: User) {
        val quest = QuestHelper.getQuestById(questRepository, questId)

        if (questParticipationRepository.existsByQuestIdAndParticipantId(user.id!!, questId)) {
            throw BusinessException(ErrorCode.ALREADY_PARTICIPATED_QUEST)
        }

        questParticipationRepository.save(QuestParticipation(quest = quest, participant = user))
    }

    @Transactional(readOnly = true)
    fun getAllQuestCount() = QuestCountResponseDto(
        totalQuestCount = QuestHelper.totalCount(questRepository),
        onProgressQuestCount = getOnProgressQuestCount(),
        totalParticipantCount = getQuestParticipantCount()
    )

    @Transactional
    fun completeQuest(questId: Long, user: User) {
        val questParticipation = questParticipationRepository.findByQuestIdAndParticipantId(questId, user.id!!)
            ?: throw BusinessException(ErrorCode.QUEST_PARTICIPATION_NOT_FOUND)

        validateCompletedQuest(questParticipation, questId, user.id!!)
        questParticipation.complete()

        val quest = questParticipation.quest
        val ability = abilityRepository.findByUserIdAndPosition(user.id!!, quest.position) ?: abilityRepository.save(
            Ability(
                user = user,
                position = quest.position
            )
        )
        ability.addPoint(PointCalculator.calculatePoint(quest.difficulty))
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

    @Transactional
    fun createQuestReview(
        questId: Long,
        questReviewCreateRequestDto: QuestReviewCreateRequestDto,
        user: User
    ) {
        val questParticipation: QuestParticipation =
            questParticipationRepository.findByQuestIdAndParticipantId(questId, user.id!!)
                ?: throw BusinessException(ErrorCode.NOT_COMPLETED_QUEST)

        if (!questParticipation.isCompleted) {
            throw BusinessException(ErrorCode.NOT_COMPLETED_QUEST)
        }

        questParticipation.review = questReviewCreateRequestDto.review
        questParticipation.reviewCreatedAt = LocalDateTime.now()

        questParticipationRepository.save(questParticipation)
    }

    @Transactional
    fun cancelQuest(questId: Long, user: User) {
        val questParticipation = findQuestParticipation(questId, user.id!!)
            ?: throw BusinessException(ErrorCode.NOT_PARTICIPATED_QUEST)
        if (questParticipation.isCompleted) throw BusinessException(ErrorCode.ALREADY_COMPLETED_QUEST)
        questParticipationRepository.delete(questParticipation)
    }

    fun getQuestParticipationStatus(questId: Long, userId: Long): String {
        val questParticipation = findQuestParticipation(questId, userId) ?: return "?????? ???????????? ?????? ??????????????????."

        return when (questParticipation.isCompleted) {
            false -> "???????????? ??????????????????."
            true -> "????????? ??????????????????."
        }
    }

    @Transactional(readOnly = true)
    fun getQuestReviewList(questId: Long, pageable: Pageable): ListResponseDto<QuestReviewResponseDto> {
        val totalReviewCount = questParticipationRepository.countByQuestIdAndReviewIsNotNull(questId)
        val reviewListVo =
            questParticipationRepository.getQuestReviewList(questId, pageable)

        return ListResponseDto(
            totalCount = totalReviewCount,
            contentList = toQuestReviewResponseDto(reviewListVo)
        )
    }

    private fun findQuestParticipation(questId: Long, userId: Long) =
        questParticipationRepository.findByQuestIdAndParticipantId(questId, userId)

    private fun getOnProgressQuestCount() = questParticipationRepository.countQuests()

    private fun getQuestParticipantCount() = questParticipationRepository.countParticipants()

    private fun toQuestReviewResponseDto(reviewListVo: List<QuestReviewVo>): List<QuestReviewResponseDto> {
        return reviewListVo.map {
            QuestReviewResponseDto(
                review = it.review,
                reviewCreatedAt = it.reviewCreatedAt,
                reviewWriter = userMapper.toDto(it.reviewWriter, it.reviewWriterPoint)
            )
        }
    }

}
