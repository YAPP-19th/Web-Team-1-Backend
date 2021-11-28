package com.yapp.giljob.domain.user.dao

import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.domain.user.vo.UserSubDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDto(user: User, point: Int): UserSubDto
}