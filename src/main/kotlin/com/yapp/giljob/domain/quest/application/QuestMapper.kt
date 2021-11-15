package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dto.QuestResponse
import com.yapp.giljob.domain.quest.dto.QuestSupportDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface QuestMapper {
    fun toDto(quest: QuestSupportDto): QuestResponse
}