package com.yapp.giljob.domain.quest.dto.response

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.tag.dto.response.TagResponseDto
import java.util.stream.Collectors

class QuestDetailCommonResponseDto(
    var name: String,
    var difficulty: Int,
    var position: Position,
    var tagList: List<TagResponseDto> = mutableListOf(),
    var participantCnt: Long,
) {
    companion object {
        fun of(quest: Quest, participantCnt: Long): QuestDetailCommonResponseDto {
            return QuestDetailCommonResponseDto(
                name = quest.name,
                difficulty =  quest.difficulty,
                position =  quest.position,
                participantCnt = participantCnt,

                tagList = quest.tagList.stream()
                    .map(TagResponseDto::of)
                    .collect(Collectors.toList())
            )
        }
    }
}