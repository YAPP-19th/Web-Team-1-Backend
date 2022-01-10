package com.yapp.giljob.domain.roadmap.dao

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.roadmap.domain.QRoadmap.roadmap
import com.yapp.giljob.domain.roadmap.domain.QRoadmapScrap
import com.yapp.giljob.domain.roadmap.vo.RoadmapSupportVo
import com.yapp.giljob.domain.user.domain.QAbility.ability
import com.yapp.giljob.domain.user.domain.QUser
import org.springframework.data.domain.Pageable

class RoadmapSupportRepositoryImpl(
    private val query: JPAQueryFactory
): RoadmapSupportRepository {
    override fun getRoadmapListByUser(
        userId: Long,
        pageable: Pageable
    ): List<RoadmapSupportVo> {

        val ids = query.select(roadmap.id)
            .from(roadmap)
            .where(roadmap.user.id.eq(userId))
            .orderBy(roadmap.id.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.pageNumber * pageable.pageSize.toLong())
            .fetch()

        return query.select(
            Projections.constructor(
                RoadmapSupportVo::class.java,
                roadmap,
                ability.point
            )
        )
            .from(roadmap)
            .where(roadmap.user.id.`in`(ids))
            .leftJoin(roadmap.user, QUser.user)
            .fetchJoin()
            .leftJoin(ability).on(ability.position.eq(roadmap.user.position).and(ability.user.id.eq(roadmap.user.id)))
            .orderBy(roadmap.id.desc())
            .fetch()
    }

    private fun ltRoadmapId(roadmapId: Long?): BooleanExpression? {
        return roadmapId?.let { QRoadmapScrap.roadmapScrap.roadmap.id.lt(roadmapId) }
    }

    override fun findRoadmapList(size: Long): List<RoadmapSupportVo> {
        return query.select(
            Projections.constructor(
                RoadmapSupportVo::class.java,
                roadmap,
                ability.point
            )
        )
            .from(roadmap)
            .leftJoin(ability).on(ability.position.eq(roadmap.user.position).and(ability.user.id.eq(roadmap.user.id)))
            .orderBy(roadmap.id.desc())
            .limit(size)
            .fetch()
    }
}