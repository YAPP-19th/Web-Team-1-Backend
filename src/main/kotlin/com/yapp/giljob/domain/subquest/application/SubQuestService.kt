package com.yapp.giljob.domain.subquest.application

import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.subquest.domain.SubQuest
import com.yapp.giljob.domain.subquest.dto.request.SubQuestRequestDto
import org.springframework.stereotype.Service

@Service
class SubQuestService {
    fun convertToSubQuestList(quest: Quest, subQuestRequestList: List<SubQuestRequestDto>) =
        subQuestRequestList.map {
            SubQuest(quest = quest, name = it.name)
        }
}