package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.QuestResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface QuestMapper {
    fun toDto(quest: Quest): QuestResponse
}