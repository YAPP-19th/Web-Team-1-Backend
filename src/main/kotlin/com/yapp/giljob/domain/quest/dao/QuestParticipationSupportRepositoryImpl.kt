package com.yapp.giljob.domain.quest.dao

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.quest.domain.QQuestParticipation
import com.yapp.giljob.domain.quest.domain.QQuestParticipation.questParticipation
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.user.domain.QAbility.ability

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

    override fun findByParticipantId(
        questId: Long?,
        participantId: Long,
        isCompleted: Boolean,
        size: Long
    ): List<QuestSupportVo> {
        val quest = questParticipation.quest
        val questParticipationCount = QQuestParticipation("questParticipationCount")

        return query
            .select(
                Projections.constructor(
                    QuestSupportVo::class.java,
                    quest,
                    ability.point,
                    JPAExpressions.select(questParticipationCount.count()).from(questParticipationCount)
                        .where(questParticipationCount.quest.eq(quest))
                )
            ).from(questParticipation)
            .where(
                (questParticipation.participant.id.eq(participantId)).and(eqIsCompleted(isCompleted)).and(ltQuestId(questId))
            )
            .leftJoin(ability).on(
                ability.position.eq(quest.user.position)
                    .and(ability.user.id.eq(quest.user.id))
            )
        .orderBy(quest.id.desc())
            .limit(size)
            .fetch()
    }


    private fun ltQuestId(questId: Long?): BooleanExpression? {
        return questId?.let { questParticipation.quest.id.lt(questId) }
 }

    private fun eqIsCompleted(isCompleted: Boolean): BooleanExpression? {
        return questParticipation.isCompleted.eq(isCompleted)
    }
}