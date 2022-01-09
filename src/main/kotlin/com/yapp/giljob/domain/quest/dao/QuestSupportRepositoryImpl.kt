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
import com.yapp.giljob.domain.quest.dto.QuestConditionDto
import com.yapp.giljob.domain.quest.vo.QuestPositionCountVo
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.quest.vo.QuestListVo
import com.yapp.giljob.domain.user.domain.QUser.user
import org.springframework.data.domain.Pageable

class QuestSupportRepositoryImpl(
    private val query: JPAQueryFactory
) : QuestSupportRepository {
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

    override fun getQuestList(conditionDto: QuestConditionDto, pageable: Pageable): QuestListVo {
        val condition = BooleanBuilder()
            .and(eqRealQuest())
            .and(eqPosition(conditionDto.position))
            .and(likeName(conditionDto.keyword)) //?.or(eqUserId(conditionDto.userId)))

        if (conditionDto.userId != null) {
            condition.and(eqUserId(conditionDto.userId))
        }

        val totalIdList = query
            .select(quest.id)
            .from(quest)
            .where(condition)
            .orderBy(quest.id.desc())

        val totalCount = totalIdList.fetchCount()

        val idList = totalIdList
            .limit(pageable.pageSize.toLong())
            .offset(pageable.pageNumber * pageable.pageSize.toLong())
            .fetch()

        val questList = if (idList.isEmpty()) {
            mutableListOf()
        } else {
            query
                .select(
                    Projections.constructor(
                        QuestSupportVo::class.java,
                        quest,
                        ability.point,
                        JPAExpressions.select(questParticipation.count())
                            .from(questParticipation)
                            .where(questParticipation.quest.eq(quest))
                    )
                )
                .distinct()
                .from(quest)
                .where(quest.id.`in`(idList))
                .leftJoin(quest.user, user)
                .fetchJoin()
                .leftJoin(ability).on(ability.position.eq(quest.user.position).and(ability.user.id.eq(quest.user.id)))
                .orderBy(quest.id.desc())
                .fetch()
        }

        return QuestListVo(totalCount, questList)
    }

    override fun getQuestPositionCount(): List<QuestPositionCountVo> {
        return query.select(
            Projections.constructor(
                QuestPositionCountVo::class.java,
                quest.position,
                quest.count()
            )
        )
            .from(quest)
            .groupBy(quest.position)
            .fetch()
    }

    private fun eqPosition(position: Position): BooleanExpression? {
        return if (position == Position.ALL) null else quest.position.eq(position)
    }

    private fun eqUserId(userId: Long?) = userId?.let { quest.user.id.eq(userId) }

    private fun eqRealQuest() = quest.isRealQuest.eq(true)

    private fun likeName(keyword: String?) = keyword?.let { quest.name.containsIgnoreCase(keyword) }
}