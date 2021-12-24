package com.yapp.giljob.domain.roadmap.dao

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.user.domain.QUser.user
import com.yapp.giljob.domain.user.domain.QAbility.ability
import com.yapp.giljob.domain.roadmap.domain.QRoadmapScrap.roadmapScrap
import com.yapp.giljob.domain.roadmap.vo.RoadmapSupportVo

class RoadmapScrapSupportRepositoryImpl(
    private val query: JPAQueryFactory
) : RoadmapScrapSupportRepository {
    override fun findByUserId(userId: Long, roadmapId: Long?, size: Long): List<RoadmapSupportVo> {

        val roadmap = roadmapScrap.roadmap
        return query.select(
            Projections.constructor(
                RoadmapSupportVo::class.java,
                roadmap,
                ability.point
            )
        )
            .from(roadmapScrap)
            .where(roadmapScrap.user.id.eq(userId).and(ltRoadmapId(roadmapId)))
            .leftJoin(ability).on(ability.position.eq(roadmap.user.position).and(ability.user.id.eq(roadmap.user.id)))
            .leftJoin(roadmap.user, user)
            .fetchJoin()
            .orderBy(roadmap.id.desc())
            .limit(size)
            .fetch()
    }

    private fun ltRoadmapId(roadmapId: Long?): BooleanExpression? {
        return roadmapId?.let { roadmapScrap.roadmap.id.lt(roadmapId) }
    }
}