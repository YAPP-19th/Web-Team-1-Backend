package com.yapp.giljob.domain.roadmap.dao

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yapp.giljob.domain.roadmap.domain.QRoadmapScrap.roadmapScrap
import com.yapp.giljob.domain.roadmap.vo.RoadmapSupportVo
import com.yapp.giljob.domain.user.domain.QAbility.ability
import com.yapp.giljob.domain.user.domain.QUser.user
import org.springframework.data.domain.Pageable

class RoadmapScrapSupportRepositoryImpl(
    private val query: JPAQueryFactory
) : RoadmapScrapSupportRepository {
    override fun findByUserId(userId: Long, pageable: Pageable): List<RoadmapSupportVo> {

        val roadmap = roadmapScrap.roadmap
        return query.select(
            Projections.constructor(
                RoadmapSupportVo::class.java,
                roadmap,
                ability.point
            )
        )
            .from(roadmapScrap)
            .where(roadmapScrap.user.id.eq(userId))
            .leftJoin(roadmap.user, user)
            .fetchJoin()
            .leftJoin(ability).on(ability.position.eq(roadmap.user.position).and(ability.user.id.eq(roadmap.user.id)))
            .orderBy(roadmap.id.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

}