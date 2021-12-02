package com.yapp.giljob.domain.subquest.application

import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import com.yapp.giljob.domain.subquest.dao.SubQuestRepository
import com.yapp.giljob.domain.subquest.domain.SubQuest
import com.yapp.giljob.domain.subquest.dto.request.SubQuestRequestDto
import com.yapp.giljob.domain.user.domain.User
import org.springframework.stereotype.Service

@Service
class SubQuestService(
    private val subQuestParticipationRepository: SubQuestParticipationRepository
) {
    fun convertToSubQuestList(quest: Quest, subQuestRequestList: List<SubQuestRequestDto>) =
        subQuestRequestList.map {
            SubQuest(quest = quest, name = it.name)
        }

    fun countCompletedSubQuest(questId: Long, participantId: Long) =
        subQuestParticipationRepository.countByQuestIdAndParticipantIdAndIsCompletedTrue(questId, participantId)
}