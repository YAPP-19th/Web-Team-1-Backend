package com.yapp.giljob.domain.tag.application

import com.yapp.giljob.domain.tag.domain.Tag
import com.yapp.giljob.domain.tag.dto.request.TagRequestDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface TagMapper {
    @Mapping(target = "id", ignore = true)
    fun toEntity(tagRequestDto: TagRequestDto): Tag
}