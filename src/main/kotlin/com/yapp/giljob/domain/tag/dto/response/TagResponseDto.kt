package com.yapp.giljob.domain.tag.dto.response

import com.yapp.giljob.domain.tag.domain.QuestTag

class TagResponseDto(
    val name: String
){
    companion object {
        fun of(questTag: QuestTag): TagResponseDto {
            return TagResponseDto(
                name = questTag.tag.name
            )
        }
    }
}