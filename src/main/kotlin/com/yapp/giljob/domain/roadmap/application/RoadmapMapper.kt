package com.yapp.giljob.domain.roadmap.application

import com.yapp.giljob.domain.roadmap.domain.Roadmap
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
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
    fun toDto(roadmap: Roadmap): RoadmapResponseDto
}