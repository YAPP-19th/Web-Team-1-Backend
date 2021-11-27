package com.yapp.giljob.domain.subquest.dao

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.subquest.vo.SubQuestCompletedCountVo
import com.yapp.giljob.domain.subquest.domain.QSubQuestParticipation.subQuestParticipation

class SubQuestParticipationSupportRepositoryImpl(
    private val query: JPAQueryFactory
) : SubQuestParticipationSupportRepository {
    override fun countSubQuestCompletedByParticipantId(participantId: Long): List<SubQuestCompletedCountVo> {
        return query.select(
            Projections.constructor(
                SubQuestCompletedCountVo::class.java,
                subQuestParticipation.quest.id,
                subQuestParticipation.count()
            )
        ).from(subQuestParticipation)
            .where(
                subQuestParticipation.participant.id.eq(participantId).and(subQuestParticipation.isCompleted.eq(true))
            )
            .groupBy(subQuestParticipation.quest)
            .fetch()
    }
}