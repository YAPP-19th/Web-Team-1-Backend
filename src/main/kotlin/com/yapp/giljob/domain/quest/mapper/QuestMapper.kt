package com.yapp.giljob.domain.quest.mapper
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.QuestRequest
import com.yapp.giljob.domain.user.domain.User
import org.mapstruct.*

@Mapper(componentModel = "spring")
interface QuestMapper {
    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "realQuest", ignore = true),
        Mapping(target = "position", source = "questRequest.position"),
        Mapping(target = "tagList", ignore = true),
        Mapping(target = "subQuestList", source = "questRequest.subQuestList"),
        Mapping(target = "user", source = "user"),
        Mapping(target = "modifiedAt", ignore = true)
    )
    fun toEntity(questRequest: QuestRequest, user: User): Quest
}