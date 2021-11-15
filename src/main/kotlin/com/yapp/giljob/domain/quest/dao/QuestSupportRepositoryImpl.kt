package com.yapp.giljob.domain.quest.dao

import com.querydsl.core.types.Projections
import com.yapp.giljob.domain.quest.domain.QQuest.quest
import com.yapp.giljob.domain.user.domain.QAbility.ability

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.quest.dto.QuestSupportDto

class QuestSupportRepositoryImpl(
    private val query: JPAQueryFactory
) : QuestSupportRepository {
    override fun findByIdLessThanAndOrderByIdDesc(id: Long?, size: Long): List<QuestSupportDto> {
        return query.select(
            Projections.constructor(
                QuestSupportDto::class.java,
                quest.id,
                quest.name,
                quest.position,
                quest.user,
                quest.difficulty,
                ability.point,
                quest.thumbnail
            )
        ).from(quest)
            .where(ltQuestId(id))
            .leftJoin(ability).on(ability.position.eq(quest.user.position))
            .orderBy(quest.id.desc())
            .limit(size)
            .fetch()
    }

    private fun ltQuestId(questId: Long?): BooleanExpression? {
        return questId?.let { quest.id.lt(questId) }
    }
}