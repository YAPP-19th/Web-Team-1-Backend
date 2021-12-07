package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dto.response.QuestByParticipantResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.vo.UserSubDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring", uses = [UserMapper::class])
interface QuestMapper {
    @Mappings(
        Mapping(target = "id", source = "questSupportVo.quest.id"),
        Mapping(target = "name", source = "questSupportVo.quest.name"),
        Mapping(target = "position", source = "questSupportVo.quest.position"),
        Mapping(target = "difficulty", source = "questSupportVo.quest.difficulty"),
        Mapping(target = "thumbnail", source = "questSupportVo.quest.thumbnail"),
        Mapping(target = "participantCount", source = "questSupportVo.participantCount"),
        Mapping(target = "user", source = "user")
    )
    fun toDto(questSupportVo: QuestSupportVo, user: UserSubDto): QuestResponseDto

    @Mappings(
        Mapping(target = "id", source = "questSupportVo.quest.id"),
        Mapping(target = "name", source = "questSupportVo.quest.name"),
        Mapping(target = "position", source = "questSupportVo.quest.position"),
        Mapping(target = "difficulty", source = "questSupportVo.quest.difficulty"),
        Mapping(target = "thumbnail", source = "questSupportVo.quest.thumbnail"),
        Mapping(target = "participantCount", source = "questSupportVo.participantCount"),
        Mapping(target = "user", source = "user"),
        Mapping(target = "progress", source = "progress"),
    )
    fun toDto(questSupportVo: QuestSupportVo, user: UserSubDto, progress: Int): QuestByParticipantResponseDto
}