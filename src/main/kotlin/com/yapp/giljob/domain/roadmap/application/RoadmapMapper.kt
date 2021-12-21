package com.yapp.giljob.domain.roadmap.application

import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.roadmap.domain.Roadmap
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapDetailResponseDto
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
import com.yapp.giljob.domain.user.dto.response.UserSubResponseDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface RoadmapMapper {
    @Mappings(
        Mapping(target = "user", source = "user"),
        Mapping(target = "name", source = "roadmap.name"),
        Mapping(target = "position", source = "roadmap.position"),
        Mapping(target = "questList", source = "questList"),
        Mapping(target = "isScraped", source = "isScraped")
    )
    fun toDto(roadmap: Roadmap, user: UserInfoResponseDto, questList: List<Quest>, isScraped: Boolean): RoadmapDetailResponseDto

    @Mappings(
        Mapping(target = "id", source = "roadmap.id"),
        Mapping(target = "name", source = "roadmap.name"),
        Mapping(target = "position", source = "roadmap.position"),
        Mapping(target = "user", source = "user")
    )
    fun toDto(roadmap: Roadmap, user: UserSubResponseDto): RoadmapResponseDto
}