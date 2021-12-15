package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestMapper
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.dto.response.QuestByParticipantResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import com.yapp.giljob.domain.subquest.vo.SubQuestCompletedCountVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserQuestService(
    private val questRepository: QuestRepository,
    private val questParticipationRepository: QuestParticipationRepository,
    private val subQuestParticipationRepository: SubQuestParticipationRepository,

    private val questMapper: QuestMapper,
    private val userMapper: UserMapper
) {
    @Transactional(readOnly = true)
    fun getQuestListByUser(userId: Long, questId: Long?, position: Position, size: Long): List<QuestResponseDto> {
        val questList = questRepository.findByIdLessThanAndOrderByIdDesc(questId, position, userId, size)

        return questList.map {
            questMapper.toDto(it, userMapper.toDto(it.quest.user, it.point))
        }
    }

    @Transactional(readOnly = true)
    fun getQuestListByParticipant(
        participantId: Long,
        questId: Long?,
        isCompleted: Boolean,
        size: Long
    ): List<QuestByParticipantResponseDto> {
        val questList = questParticipationRepository.findByParticipantId(questId, participantId, isCompleted, size)
        if (isCompleted) {
            return getCompletedQuestListByParticipant(questList)
        }

        val subQuestCompletedCountList =
            subQuestParticipationRepository.countSubQuestCompletedByParticipantId(participantId)
                .associateBy { it.questId }

        return getNotCompletedQuestListByParticipant(questList, subQuestCompletedCountList)
    }

    private fun getCompletedQuestListByParticipant(questList: List<QuestSupportVo>) =
        questList.map {
            questMapper.toCompletedDto(
                it, userMapper.toDto(it.quest.user, it.point)
            )
        }

    private fun getNotCompletedQuestListByParticipant(
        questList: List<QuestSupportVo>,
        subQuestCompletedCountList: Map<Long, SubQuestCompletedCountVo>
    ) =
        questList.map {
            questMapper.toDto(
                it, userMapper.toDto(it.quest.user, it.point),
                calculateProgress(it.quest.subQuestList.size, subQuestCompletedCountList[it.quest.id]?.count ?: 0L)
            )
        }

    fun calculateProgress(totalSubQuestCount: Int, subQuestCompletedCount: Long) =
        subQuestCompletedCount.toDouble().div(totalSubQuestCount).times(100).toInt()
}