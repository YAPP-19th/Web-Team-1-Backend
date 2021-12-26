package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.request.QuestRequestDto
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailInfoResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailSubQuestResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.roadmap.domain.Roadmap
import com.yapp.giljob.domain.roadmap.domain.RoadmapQuest
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import com.yapp.giljob.domain.subquest.dto.response.SubQuestProgressResponseDto
import com.yapp.giljob.domain.tag.application.TagService
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.application.UserQuestService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
    private val questRepository: QuestRepository,
    private val subQuestParticipationRepository: SubQuestParticipationRepository,

    private val subQuestService: SubQuestService,
    private val tagService: TagService,
    private val userQuestService: UserQuestService,

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
        return questMapper.toQuestDetailInfoDto(questSupportVo, userMapper.toDto(questSupportVo.quest.user, questSupportVo.point))
    }

    fun convertToQuestList(roadmap: Roadmap, questList: List<QuestRequestDto>) : List<RoadmapQuest> {
        val roadmapQuestList: MutableList<RoadmapQuest> = mutableListOf()
        for (quest in questList) {

            val questToSave =
                quest.questId?. let {
                    QuestHelper.getQuestById(questRepository, quest.questId)
                } ?: run {
                    questRepository.save(
                        Quest(
                            user = roadmap.user,
                            name = quest.name!!,
                            isRealQuest = false)
                    )
                }

            roadmapQuestList.add(RoadmapQuest(
                roadmap = roadmap,
                quest = questToSave
            ))
        }

        return roadmapQuestList
    }
}
