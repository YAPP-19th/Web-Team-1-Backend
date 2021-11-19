package com.yapp.giljob.domain.quest.dao

import com.querydsl.core.types.Projections
import com.yapp.giljob.domain.quest.domain.QQuest.quest
import com.yapp.giljob.domain.user.domain.QAbility.ability

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.vo.QuestSupportVo

class QuestSupportRepositoryImpl(
    private val query: JPAQueryFactory
) : QuestSupportRepository {
    override fun findByIdLessThanAndOrderByIdDesc(id: Long?, position: Position, size: Long): List<QuestSupportVo> {
        return query.select(
            Projections.constructor(
                QuestSupportVo::class.java,
                quest.id,
                quest.name,
                quest.position,
                quest.user,
                quest.difficulty,
                ability.point,
                quest.thumbnail
            )
        ).from(quest)
            .where(eqPosition(position)?.and(ltQuestId(id)) ?: ltQuestId(id))
            .leftJoin(ability).on(ability.position.eq(quest.user.position).and(ability.user.id.eq(quest.user.id)))
            .orderBy(quest.id.desc())
            .limit(size)
            .fetch()
    }

    private fun ltQuestId(questId: Long?): BooleanExpression? {
        return questId?.let { quest.id.lt(questId) }
    }
    private fun eqPosition(position: Position): BooleanExpression? {
        return if (position == Position.ALL) null else quest.position.eq(position)
    }
}