package com.yapp.giljob.domain.user.dto.response

import com.yapp.giljob.domain.position.domain.Position

class UserInfoResponseDto(
    var userId: Long,
    var nickname: String,
    var position: Position,
    var point: Long,
    var intro: String
)