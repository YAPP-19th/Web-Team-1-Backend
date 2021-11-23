package com.yapp.giljob.domain.quest.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.quest.domain.QQuestParticipation.questParticipation

class QuestParticipationSupportRepositoryImpl(
    private val query: JPAQueryFactory
) : QuestParticipationSupportRepository {
    override fun countParticipants(): Long {
        return query.from(questParticipation)
            .select(questParticipation.id.participantId)
            .distinct()
            .fetchCount()
    }

    override fun countQuests(): Long {
        return query.from(questParticipation)
            .select(questParticipation.id.questId)
            .distinct()
            .fetchCount()
    }
}