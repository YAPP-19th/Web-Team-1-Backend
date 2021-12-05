package com.yapp.giljob.domain.subquest.dao

import com.yapp.giljob.domain.subquest.domain.SubQuestParticipation
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipationPK
import org.springframework.data.jpa.repository.JpaRepository

interface SubQuestParticipationRepository : JpaRepository<SubQuestParticipation, SubQuestParticipationPK>, SubQuestParticipationSupportRepository {
    fun countByQuestIdAndParticipantIdAndIsCompletedTrue(questId: Long, participantId: Long): Int
}
