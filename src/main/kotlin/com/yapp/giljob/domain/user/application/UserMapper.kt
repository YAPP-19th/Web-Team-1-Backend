package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.user.domain.Ability
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.domain.user.dto.response.AbilityResponseDto
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDto(user: User, point: Long): UserInfoResponseDto
    fun toDto(ability: Ability): AbilityResponseDto

    @Mappings(
        Mapping(target = "id", source = "user.id"),
        Mapping(target = "nickname", source = "user.nickname"),
        Mapping(target = "intro", source = "user.intro"),
        Mapping(target = "position", source = "ability.position"),
        Mapping(target = "point", source = "ability.point")
    )
    fun toDto(user: User, ability: AbilityResponseDto): UserInfoResponseDto
}