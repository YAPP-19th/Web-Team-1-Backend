package com.yapp.giljob.domain.roadmap.dao

import com.yapp.giljob.domain.roadmap.vo.RoadmapSupportVo

interface RoadmapScrapSupportRepository {
    fun findByUserId(userId: Long, roadmapId: Long?, size: Long): List<RoadmapSupportVo>
}