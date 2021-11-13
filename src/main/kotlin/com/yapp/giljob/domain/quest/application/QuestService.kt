package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.QuestRequest
import com.yapp.giljob.domain.quest.dto.QuestResponse
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.tag.application.TagService
import com.yapp.giljob.domain.user.domain.User
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
    private val questRepository: QuestRepository,
    private val subQuestService: SubQuestService,
    private val tagService: TagService,
    private val questMapper: QuestMapper
) {
    @Transactional
    fun saveQuest(questRequest: QuestRequest, user: User): Quest {
        val quest = Quest.of(questRequest, user)

        quest.subQuestList.addAll(subQuestService.convertToSubQuestList(quest, questRequest.subQuestList))
        quest.tagList.addAll(tagService.convertToQuestTagList(quest, questRequest.tagList))

        return questRepository.save(quest)
    }

    @Transactional(readOnly = true)
    fun getQuestList(questId: Long?, pageable: Pageable): List<QuestResponse> {
        val questList = questId?.let {
            questRepository.findByIdLessThanOrderByIdDesc(questId, pageable)
        } ?: questRepository.findByOrderByIdDesc(pageable)

        return questList.map {
            questMapper.toDto(it)
        }
    }
}