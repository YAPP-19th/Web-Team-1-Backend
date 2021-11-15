package com.yapp.giljob.domain.quest.dto

import com.yapp.giljob.domain.position.domain.Position

class QuestResponse(
    val id: Long,
    val name: String,
    val position: Position,
    val user: UserDto,
    val difficulty: Int,
    val point: Int,
    val thumbnail: String
) {
    class UserDto(
        val id: Long,
        val nickname: String
    )
}