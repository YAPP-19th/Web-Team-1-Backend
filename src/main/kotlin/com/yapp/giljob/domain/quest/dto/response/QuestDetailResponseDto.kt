package com.yapp.giljob.domain.quest.dto.response

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto

class QuestDetailResponseDto(
    var id: Long,
    var name: String,
    var position: Position,
    var writer: UserInfoResponseDto,
    var difficulty: Int,
    var thumbnail: String?,
    var participantCount: Long
)