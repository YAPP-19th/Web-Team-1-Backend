package com.yapp.giljob.domain.quest.dto.response

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.user.vo.UserSubVo

class QuestResponseDto(
    var id: Long,
    var name: String,
    var position: Position,
    var user: UserSubVo,
    var difficulty: Int,
    var thumbnail: String
)