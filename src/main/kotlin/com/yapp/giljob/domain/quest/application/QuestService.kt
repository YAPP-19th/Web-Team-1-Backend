package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailCommonResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.tag.application.TagService
import com.yapp.giljob.domain.user.dao.UserMapper
import com.yapp.giljob.domain.user.domain.User
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
    fun getQuestList(questId: Long?, position: Position, size: Long): List<QuestResponseDto> {
        val questList =
            questRepository.findByIdLessThanAndOrderByIdDesc(questId = questId, position = position, size = size)

        return questList.map {
            questMapper.toDto(it, userMapper.toDto(it.quest.user, it.point))
        }
    }

    fun getQuestDetailCommon(questId: Long): QuestDetailCommonResponseDto {
        val participantCnt = questRepository.countParticipantsByQuestId(questId)
        val quest = QuestHelper.getQuestById(questRepository, questId)
        return QuestDetailCommonResponseDto.of(quest, participantCnt)
    }
}
