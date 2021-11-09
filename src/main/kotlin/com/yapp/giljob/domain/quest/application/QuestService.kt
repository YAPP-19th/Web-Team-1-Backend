package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.tag.dao.TagRepository
import com.yapp.giljob.domain.tag.domain.QuestTag
import com.yapp.giljob.domain.tag.domain.QuestTagPK
import com.yapp.giljob.domain.tag.domain.Tag
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
    private val questRepository: QuestRepository,
    private val tagRepository: TagRepository
) {
    @Transactional
    fun saveQuest(quest: Quest, tagList: List<Tag>): Quest {
        quest.subQuestList.forEach { it.quest = quest }
        tagList.forEach {
            val tag = getTagWithId(it)
            quest.tagList.add(QuestTag(QuestTagPK(quest.id, tag.id)))
        }
        return questRepository.save(quest)
    }

    private fun getTagWithId(tag: Tag) = tagRepository.findByName(tag.name) ?: tagRepository.save(tag)
}