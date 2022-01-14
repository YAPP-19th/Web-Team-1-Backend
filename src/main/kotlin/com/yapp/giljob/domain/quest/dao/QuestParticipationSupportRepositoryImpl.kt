package com.yapp.giljob.domain.quest.dao

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.quest.domain.QQuestParticipation
import com.yapp.giljob.domain.quest.domain.QQuestParticipation.questParticipation
import com.yapp.giljob.domain.quest.vo.QuestListVo
import com.yapp.giljob.domain.quest.vo.QuestReviewVo
import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.user.domain.QAbility.ability
import org.springframework.data.domain.Pageable

class QuestParticipationSupportRepositoryImpl(
    private val query: JPAQueryFactory
) : QuestParticipationSupportRepository {
    override fun countParticipants(): Long {
        return query.from(questParticipation)
            .select(questParticipation.participant)
            .distinct()
            .fetchCount()
    }

    override fun countQuests(): Long {
        return query.from(questParticipation)
            .select(questParticipation.quest)
            .distinct()
            .fetchCount()
    }

    override fun findByParticipantId(participantId: Long, isCompleted: Boolean, pageable: Pageable): QuestListVo {
        val quest = questParticipation.quest
        val questParticipationCount = QQuestParticipation("questParticipationCount")

        val totalQuestList = query
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
                (questParticipation.participant.id.eq(participantId)).and(eqIsCompleted(isCompleted))
            )
            .leftJoin(ability).on(
                ability.position.eq(quest.user.position)
                    .and(ability.user.id.eq(quest.user.id))
            )
            .orderBy(questParticipation.id.desc())

        val totalCount = totalQuestList.fetchCount()

        val questList = totalQuestList
            .limit(pageable.pageSize.toLong())
            .offset(pageable.pageNumber * pageable.pageSize.toLong())
            .fetch()

        return QuestListVo(totalCount, questList)
    }

    override fun getQuestReviewList(
        questId: Long,
        pageable: Pageable,
    ): List<QuestReviewVo> {
        val ids = query.select(questParticipation.id)
            .from(questParticipation)
            .where((questParticipation.quest.id.eq(questId)).and(questParticipation.review.isNotNull))
            .orderBy(questParticipation.reviewCreatedAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return query
            .select(
                Projections.constructor(
                    QuestReviewVo::class.java,
                    questParticipation.review,
                    questParticipation.reviewCreatedAt,
                    questParticipation.participant,
                    ability.point
                )
            ).from(questParticipation)
            .where(questParticipation.id.`in`(ids))
            .leftJoin(ability).on(
                ability.position.eq(questParticipation.participant.position)
                    .and(ability.user.id.eq(questParticipation.participant.id))
            )
            .orderBy(questParticipation.reviewCreatedAt.desc())
            .fetch()
    }

    private fun eqIsCompleted(isCompleted: Boolean): BooleanExpression {
        return questParticipation.isCompleted.eq(isCompleted)
    }

}