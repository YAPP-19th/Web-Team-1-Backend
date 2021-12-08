package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.user.domain.Ability
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.domain.user.dto.response.AbilityResponseDto
import com.yapp.giljob.domain.user.dto.response.UserSubResponseDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDto(user: User, point: Long): UserSubResponseDto
    fun toDto(ability: Ability): AbilityResponseDto
}