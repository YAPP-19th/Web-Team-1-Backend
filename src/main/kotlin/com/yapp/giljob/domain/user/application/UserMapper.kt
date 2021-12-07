package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.user.domain.Ability
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.domain.user.dto.response.AbilityResponseDto
import com.yapp.giljob.domain.user.vo.UserSubDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDto(user: User, point: Long): UserSubDto
    fun toDto(ability: Ability): AbilityResponseDto
}