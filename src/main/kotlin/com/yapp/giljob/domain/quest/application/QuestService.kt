package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailInfoResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.tag.application.TagService
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
    private val questRepository: QuestRepository,
    private val questParticipationRepository: QuestParticipationRepository,

    private val subQuestService: SubQuestService,
    private val tagService: TagService,

    private val questMapper: QuestMapper,
    private val userMapper: UserMapper
) {
    @Transactional
    fun saveQuest(questSaveRequestDto: QuestSaveRequestDto, user: User): Quest {
        val quest = Quest.of(questSaveRequestDto, user)

        quest.subQuestList.addAll(subQuestService.convertToSubQuestList(quest, questSaveRequestDto.subQuestList))
        quest.tagList.addAll(tagService.convertToQuestTagList(quest, questSaveRequestDto.tagList))

        return questRepository.save(quest)
    }

    @Transactional(readOnly = true)
    fun getQuestList(questId: Long?, position: Position, size: Long): List<QuestResponseDto> {
        val questList =
            questRepository.findByIdLessThanAndOrderByIdDesc(questId = questId, position = position, size = size)

        return questList.map {
            questMapper.toDto(it, userMapper.toDto(it.quest.user, it.point))
        }
    }

    @Transactional(readOnly = true)
    fun getQuestDetailInfo(questId: Long): QuestDetailInfoResponseDto {
        val questSupportVo = questRepository.findByQuestId(questId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        val tagResponseDtoList = tagService.convertToTagResponseDtoList(questSupportVo.quest)

        return questMapper.toQuestDetailInfoDto(
            questSupportVo,
            userMapper.toDto(questSupportVo.quest.user, questSupportVo.point),
            tagResponseDtoList)
    }

    fun getUserQuestStatus(questId: Long, user: User?): String {
        user ?: return "로그인한 유저가 없습니다."

        val questParticipation
        = questParticipationRepository.getQuestParticipationByQuestIdAndParticipantId(questId, user.id!!)
            ?: return "아직 참여하지 않은 퀘스트입니다."

        return when(questParticipation.isCompleted) {
            false -> "참여중인 퀘스트입니다."
            true -> "완료한 퀘스트입니다."
        }
    }
}
