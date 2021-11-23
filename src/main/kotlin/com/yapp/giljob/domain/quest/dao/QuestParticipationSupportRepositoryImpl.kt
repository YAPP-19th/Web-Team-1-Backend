package com.yapp.giljob.domain.quest.dao

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.QQuestParticipation.questParticipation
import com.yapp.giljob.domain.user.domain.QAbility.ability
import com.yapp.giljob.domain.quest.vo.QuestSupportVo

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
        position: Position,
        size: Long
    ): List<QuestSupportVo> {
        return query
            .select(
                Projections.constructor(
                    QuestSupportVo::class.java,
                    questParticipation.quest.id,
                    questParticipation.quest.name,
                    questParticipation.quest.position,
                    questParticipation.quest.user,
                    questParticipation.quest.difficulty,
                    ability.point,
                    questParticipation.quest.thumbnail
                )
            ).from(questParticipation)
            .where(
                (questParticipation.participant.id.eq(participantId)).and(eqPosition(position)).and(ltQuestId(questId))
            )
            .leftJoin(ability).on(
                ability.position.eq(questParticipation.quest.user.position)
                    .and(ability.user.id.eq(questParticipation.quest.user.id))
            )
            .orderBy(questParticipation.quest.id.desc())
            .limit(size)
            .fetch()
    }


    private fun ltQuestId(questId: Long?): BooleanExpression? {
        return questId?.let { questParticipation.quest.id.lt(questId) }
    }

    private fun eqPosition(position: Position): BooleanExpression? {
        return if (position == Position.ALL) null else questParticipation.quest.position.eq(position)
    }
}