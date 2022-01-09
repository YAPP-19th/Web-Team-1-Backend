package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.QuestConditionDto
import com.yapp.giljob.domain.quest.dto.request.QuestRequestDto
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailInfoResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestPositionCountResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.roadmap.domain.Roadmap
import com.yapp.giljob.domain.roadmap.domain.RoadmapQuest
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.tag.application.TagService
import com.yapp.giljob.domain.tag.dto.response.TagResponseDto
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
    private val questRepository: QuestRepository,

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
    fun getQuestList(conditionDto: QuestConditionDto, pageable: Pageable): QuestResponseDto<QuestDetailResponseDto> {
        val questListVo = questRepository.getQuestList(conditionDto, pageable)

        return QuestResponseDto(questListVo.totalCount, questListVo.questList.map {
            questMapper.toDto(it, userMapper.toDto(it.quest.user, it.point))
        })
    }

    @Transactional(readOnly = true)
    fun getQuestDetailInfo(questId: Long): QuestDetailInfoResponseDto {
        val questSupportVo =
            questRepository.findByQuestId(questId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        return convertToQuestDetailInfo(questSupportVo)
    }

    fun convertToQuestList(roadmap: Roadmap, questList: List<QuestRequestDto>): List<RoadmapQuest> {
        val roadmapQuestList: MutableList<RoadmapQuest> = mutableListOf()
        for (quest in questList) {
            val questToSave =
                quest.questId?.let {
                    QuestHelper.getQuestById(questRepository, quest.questId)
                } ?: run {
                    questRepository.save(
                        Quest(
                            user = roadmap.user,
                            name = quest.name!!,
                            isRealQuest = false,
                            position = roadmap.position
                        )
                    )
                }

            roadmapQuestList.add(
                RoadmapQuest(
                    roadmap = roadmap,
                    quest = questToSave
                )
            )
        }

        return roadmapQuestList
    }

    private fun convertToQuestDetailInfo(questSupportVo: QuestSupportVo): QuestDetailInfoResponseDto {
        val questDetailInfoResponseDto = questMapper.toQuestDetailInfoDto(
            questSupportVo,
            userMapper.toDto(questSupportVo.quest.user, questSupportVo.point)
        )
        questDetailInfoResponseDto.tagList = questSupportVo.quest.tagList.map {
            TagResponseDto(it.tag.name)
        }
        return questDetailInfoResponseDto
    }

    fun getQuestPositionCount(): List<QuestPositionCountResponseDto> {
        val questPositionCountVoList = questRepository.getQuestPositionCount()
        return questPositionCountVoList.map {
            QuestPositionCountResponseDto(position = it.position.name, questCount = it.questCount)
        }
    }
}
