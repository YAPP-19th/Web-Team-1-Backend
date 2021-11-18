package com.yapp.giljob.domain.quest.dto.response

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.user.dto.UserSubDto

class QuestResponseDto(
    var id: Long,
    var name: String,
    var position: Position,
    var user: UserSubDto,
    var difficulty: Int,
    var thumbnail: String
)