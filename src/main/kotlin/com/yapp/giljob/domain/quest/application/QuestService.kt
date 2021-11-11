package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.QuestRequest
import com.yapp.giljob.domain.subquest.domain.SubQuest
import com.yapp.giljob.domain.tag.dao.TagRepository
import com.yapp.giljob.domain.tag.domain.QuestTag
import com.yapp.giljob.domain.tag.domain.Tag
import com.yapp.giljob.domain.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
    private val questRepository: QuestRepository,
    private val tagRepository: TagRepository
) {
    @Transactional
    fun saveQuest(questRequest: QuestRequest, user: User) : Quest {
        val quest = Quest.of(questRequest, user)

        questRequest.subQuestList.forEach {
            quest.subQuestList.add(SubQuest(quest = quest, name = it.name))
        }

        questRequest.tagList.forEach {
            quest.tagList.add(QuestTag(quest = quest, tag = getTagWithId(it.name)))
        }

        return questRepository.save(quest)
    }

    private fun getTagWithId(name: String) = tagRepository.findByName(name) ?: tagRepository.save(Tag(name = name))
}