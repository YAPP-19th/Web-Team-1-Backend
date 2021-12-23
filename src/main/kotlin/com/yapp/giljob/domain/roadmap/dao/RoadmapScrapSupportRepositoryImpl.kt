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
                roadmap.user,
                ability.point
            )
        )
            .from(roadmapScrap)
            .leftJoin(roadmapScrap.user, user)
            .leftJoin(ability).on(ability.position.eq(roadmap.user.position).and(ability.user.id.eq(roadmap.user.id)))
            .where(roadmapScrap.user.id.eq(userId).and(ltRoadmapId(roadmapId)).and(roadmap.deleted.eq(false)))
            .orderBy(roadmap.id.desc())
            .limit(size)
            .fetch()
    }

    private fun ltRoadmapId(roadmapId: Long?): BooleanExpression? {
        return roadmapId?.let { roadmapScrap.roadmap.id.lt(roadmapId) }
    }
}