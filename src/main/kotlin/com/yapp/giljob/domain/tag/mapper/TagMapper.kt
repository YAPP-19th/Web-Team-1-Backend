package com.yapp.giljob.domain.tag.mapper

import com.yapp.giljob.domain.tag.domain.Tag
import com.yapp.giljob.domain.tag.dto.TagRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface TagMapper {
    @Mapping(target = "id", ignore = true)
    fun toEntity(tagRequest: TagRequest): Tag
}