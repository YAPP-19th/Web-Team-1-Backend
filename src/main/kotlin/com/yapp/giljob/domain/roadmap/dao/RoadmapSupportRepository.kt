package com.yapp.giljob.domain.roadmap.dao

import com.yapp.giljob.domain.roadmap.vo.RoadmapSupportVo

interface RoadmapSupportRepository {
    fun findByUserAndIdLessThanAndOrderByIdDesc(userId: Long, cursor: Long?, size: Long): List<RoadmapSupportVo>
}