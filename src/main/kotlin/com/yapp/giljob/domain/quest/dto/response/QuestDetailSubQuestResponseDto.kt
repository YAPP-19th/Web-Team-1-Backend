package com.yapp.giljob.domain.quest.dto.response

import com.yapp.giljob.domain.subquest.dto.response.SubQuestProgressResponseDto

class QuestDetailSubQuestResponseDto (
    val progress: Int,
    val subQuestProgressList: List<SubQuestProgressResponseDto>
    )