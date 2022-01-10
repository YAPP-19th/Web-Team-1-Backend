package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dto.response.QuestByParticipantResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailInfoResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailResponseDto
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
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
        Mapping(target = "writer", source = "user")
    )
    fun toDto(questSupportVo: QuestSupportVo, user: UserInfoResponseDto): QuestDetailResponseDto

    @Mappings(
        Mapping(target = "id", source = "questSupportVo.quest.id"),
        Mapping(target = "name", source = "questSupportVo.quest.name"),
        Mapping(target = "position", source = "questSupportVo.quest.position"),
        Mapping(target = "difficulty", source = "questSupportVo.quest.difficulty"),
        Mapping(target = "thumbnail", source = "questSupportVo.quest.thumbnail"),
        Mapping(target = "participantCount", source = "questSupportVo.participantCount"),
        Mapping(target = "writer", source = "user"),
        Mapping(target = "progress", source = "progress")
    )
    fun toDto(questSupportVo: QuestSupportVo, user: UserInfoResponseDto, progress: Int): QuestByParticipantResponseDto

    @Mappings(
        Mapping(target = "id", source = "questSupportVo.quest.id"),
        Mapping(target = "name", source = "questSupportVo.quest.name"),
        Mapping(target = "position", source = "questSupportVo.quest.position"),
        Mapping(target = "difficulty", source = "questSupportVo.quest.difficulty"),
        Mapping(target = "thumbnail", source = "questSupportVo.quest.thumbnail"),
        Mapping(target = "participantCount", source = "questSupportVo.participantCount"),
        Mapping(target = "writer", source = "user"),
        Mapping(target = "progress", constant = "100")
    )
    fun toCompletedDto(questSupportVo: QuestSupportVo, user: UserInfoResponseDto): QuestByParticipantResponseDto

    @Mappings(
        Mapping(target = "id", source = "questSupportVo.quest.id"),
        Mapping(target = "name", source = "questSupportVo.quest.name"),
        Mapping(target = "position", source = "questSupportVo.quest.position"),
        Mapping(target = "difficulty", source = "questSupportVo.quest.difficulty"),
        Mapping(target = "tagList", source = "questSupportVo.quest.tagList"),
        Mapping(target = "detail", source = "questSupportVo.quest.detail"),
        Mapping(target = "participantCount", source = "questSupportVo.participantCount"),
        Mapping(target = "writer", source = "writer")
    )
    fun toQuestDetailInfoDto(questSupportVo: QuestSupportVo, writer: UserInfoResponseDto): QuestDetailInfoResponseDto
}