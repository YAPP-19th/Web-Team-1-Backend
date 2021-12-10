package com.yapp.giljob.domain.user.dto.request

import com.yapp.giljob.domain.position.domain.Position

class UserInfoUpdateRequestDto(
    val nickname: String,
    val position: Position
)