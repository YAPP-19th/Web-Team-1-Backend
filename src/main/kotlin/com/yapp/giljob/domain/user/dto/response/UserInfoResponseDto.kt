package com.yapp.giljob.domain.user.dto.response

import com.yapp.giljob.domain.position.domain.Position

class UserInfoResponseDto(
    var id: Long,
    var nickname: String,
    var position: Position,
    var point: Long,
    var intro: String
)