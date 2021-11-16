package com.yapp.giljob.domain.quest.dto

import com.yapp.giljob.domain.position.domain.Position

class QuestResponse(
    var id: Long,
    var name: String,
    var position: Position,
    var user: UserDto,
    var difficulty: Int,
    var point: Int,
    var thumbnail: String
) {
    class UserDto(
        var id: Long,
        var nickname: String
    )
}