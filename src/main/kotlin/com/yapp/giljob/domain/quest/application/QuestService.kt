package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.tag.application.TagService
import com.yapp.giljob.domain.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
    private val questRepository: QuestRepository,
    private val subQuestService: SubQuestService,
    private val tagService: TagService
) {
    @Transactional
    fun saveQuest(questSaveRequestDto: QuestSaveRequestDto, user: User) : Quest {
        val quest = Quest.of(questSaveRequestDto, user)

        quest.subQuestList.addAll(subQuestService.convertToSubQuestList(quest, questSaveRequestDto.subQuestList))
        quest.tagList.addAll(tagService.convertToQuestTagList(quest, questSaveRequestDto.tagList))

        return questRepository.save(quest)
    }
}