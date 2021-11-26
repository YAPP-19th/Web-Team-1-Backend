package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dto.response.QuestByParticipantResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.user.dao.UserMapper
import com.yapp.giljob.domain.user.vo.UserSubDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring", uses = [UserMapper::class])
interface QuestMapper {
    @Mappings(
        Mapping(target = "id", source = "quest.quest.id"),
        Mapping(target = "name", source = "quest.quest.name"),
        Mapping(target = "position", source = "quest.quest.position"),
        Mapping(target = "difficulty", source = "quest.quest.difficulty"),
        Mapping(target = "thumbnail", source = "quest.quest.thumbnail"),
        Mapping(target = "user", source = "user")
    )
    fun toDto(quest: QuestSupportVo, user: UserSubDto): QuestResponseDto

    @Mappings(
        Mapping(target = "id", source = "quest.quest.id"),
        Mapping(target = "name", source = "quest.quest.name"),
        Mapping(target = "position", source = "quest.quest.position"),
        Mapping(target = "difficulty", source = "quest.quest.difficulty"),
        Mapping(target = "thumbnail", source = "quest.quest.thumbnail"),
        Mapping(target = "user", source = "user"),
        Mapping(target = "progress", source = "progress"),
    )
    fun toDto(quest: QuestSupportVo, user: UserSubDto, progress: Int): QuestByParticipantResponseDto
}