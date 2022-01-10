package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.quest.application.QuestMapper
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.dto.QuestConditionDto
import com.yapp.giljob.domain.quest.dto.response.QuestByParticipantResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailResponseDto
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import com.yapp.giljob.domain.subquest.vo.SubQuestCompletedCountVo
import com.yapp.giljob.global.common.dto.ListResponseDto
import com.yapp.giljob.global.util.SubQuestProgressCalculate.Companion.calculateProgress
import org.springframework.data.domain.Pageable
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
    fun getQuestListByUser(conditionDto: QuestConditionDto, pageable: Pageable): ListResponseDto<QuestDetailResponseDto> {
        val questListVo = questRepository.getQuestList(conditionDto, pageable)

        return ListResponseDto(questListVo.totalCount, questListVo.questList.map {
            questMapper.toDto(it, userMapper.toDto(it.quest.user, it.point))
        })
    }

    @Transactional(readOnly = true)
    fun getQuestListByParticipant(
        participantId: Long,
        isCompleted: Boolean,
        pageable: Pageable
    ): ListResponseDto<QuestByParticipantResponseDto> {
        val questListVo = questParticipationRepository.findByParticipantId(participantId, isCompleted, pageable)

        if (isCompleted) {
            return ListResponseDto(questListVo.totalCount, getCompletedQuestListByParticipant(questListVo.questList))
        }

        val subQuestCompletedCountList =
            subQuestParticipationRepository.countSubQuestCompletedByParticipantId(participantId)
                .associateBy { it.questId }

        return ListResponseDto(questListVo.totalCount, getNotCompletedQuestListByParticipant(questListVo.questList, subQuestCompletedCountList))
    }

    private fun getCompletedQuestListByParticipant(questList: List<QuestSupportVo>) =
        questList.map {
            questMapper.toCompletedDto(
                it, userMapper.toDto(it.quest.user, it.point)
            )
        }

    private fun getNotCompletedQuestListByParticipant(
        questList: List<QuestSupportVo>,
        subQuestCompletedCountList: Map<Long, SubQuestCompletedCountVo>): List<QuestByParticipantResponseDto> {
        return questList.map {
            questMapper.toDto(
                it, userMapper.toDto(it.quest.user, it.point),
                calculateProgress(it.quest.subQuestList.size, subQuestCompletedCountList[it.quest.id]?.count ?: 0L)
            )
        }
    }
}