package com.yapp.giljob.domain.tag.application

import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.tag.dao.TagRepository
import com.yapp.giljob.domain.tag.domain.QuestTag
import com.yapp.giljob.domain.tag.domain.Tag
import com.yapp.giljob.domain.tag.dto.request.TagRequestDto
import com.yapp.giljob.domain.tag.dto.response.TagResponseDto
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepository: TagRepository
) {
    fun convertToQuestTagList(quest: Quest, tagList: List<TagRequestDto>) =
        tagList.map {
            QuestTag(quest = quest, tag = saveTag(it.name))
        }

    private fun saveTag(name: String) = tagRepository.findByName(name) ?: tagRepository.save(Tag(name = name))

    fun convertToTagResponseDtoList(quest: Quest) =
        quest.tagList.map {
            TagResponseDto(name = it.tag.name)
        }
}