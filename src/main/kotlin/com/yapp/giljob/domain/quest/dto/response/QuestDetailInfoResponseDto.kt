package com.yapp.giljob.domain.quest.dto.response

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.tag.dto.response.TagResponseDto
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto

class QuestDetailInfoResponseDto(
    var id: Long,
    var name: String,
    var position: Position,
    var difficulty: Int,
    var tagList: List<TagResponseDto> = mutableListOf(),
    var detail: String,
    var participantCount: Long,
    var writer: UserInfoResponseDto
)