package com.yapp.giljob.domain.quest.dao

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.yapp.giljob.domain.quest.domain.QQuest.quest
import com.yapp.giljob.domain.user.domain.QAbility.ability

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.QQuestParticipation.questParticipation
import com.yapp.giljob.domain.quest.vo.QuestSupportVo

class QuestSupportRepositoryImpl(
    private val query: JPAQueryFactory
) : QuestSupportRepository {
    override fun findByIdLessThanAndOrderByIdDesc(
        questId: Long?,
        position: Position,
        userId: Long?,
        size: Long
    ): List<QuestSupportVo> {
        val builder = BooleanBuilder()

        if (userId == null) {
            builder.and(eqPosition(position)).and(ltQuestId(questId))
        } else builder.and(eqUserId(userId)).and(eqPosition(position)).and(ltQuestId(questId))

        return selectQuestList(builder, size)
    }

    override fun search(keyword: String, position: Position, size: Long, questId: Long?): List<QuestSupportVo> {
        val builder = BooleanBuilder()

        builder
            .and(eqPosition(position))
            .and(ltQuestId(questId))
            .and(quest.name.containsIgnoreCase(keyword))

        return selectQuestList(builder, size)
    }

    private fun selectQuestList(builder: BooleanBuilder, size: Long) = query.select(
        Projections.constructor(
            QuestSupportVo::class.java,
            quest,
            ability.point,
            JPAExpressions.select(questParticipation.count()).from(questParticipation)
                .where(questParticipation.quest.eq(quest))
        )
    ).distinct()
        .from(quest)
        .where(builder)
        .leftJoin(ability).on(ability.position.eq(quest.user.position).and(ability.user.id.eq(quest.user.id)))
        .orderBy(quest.id.desc())
        .limit(size)
        .fetch()

    private fun ltQuestId(questId: Long?): BooleanExpression? {
        return questId?.let { quest.id.lt(questId) }
    }

    private fun eqPosition(position: Position): BooleanExpression? {
        return if (position == Position.ALL) null else quest.position.eq(position)
    }

    private fun eqUserId(userId: Long): BooleanExpression {
        return quest.user.id.eq(userId)
    }

    override fun countParticipantsByQuestId(questId: Long): Long {
        return query.from(questParticipation)
            .select(questParticipation.id.count())
            .where(questParticipation.quest.id.eq(questId))
            .fetchCount()
    }

    override fun findByQuestId(questId: Long): QuestSupportVo? {
        return query.select(
            Projections.constructor(
                QuestSupportVo::class.java,
                quest,
                ability.point,
                JPAExpressions.select(questParticipation.count()).from(questParticipation)
                    .where(questParticipation.quest.eq(quest))
            )
        ).distinct()
            .from(quest)
            .where(quest.id.eq(questId))
            .leftJoin(ability).on(ability.position.eq(quest.user.position).and(ability.user.id.eq(quest.user.id)))
            .fetchOne()
    }
}