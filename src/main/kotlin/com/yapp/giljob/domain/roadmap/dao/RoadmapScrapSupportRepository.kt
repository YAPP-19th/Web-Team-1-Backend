package com.yapp.giljob.domain.roadmap.dao

import com.yapp.giljob.domain.roadmap.vo.RoadmapSupportVo
import org.springframework.data.domain.Pageable

interface RoadmapScrapSupportRepository {
    fun findByUserId(userId: Long, pageable: Pageable): List<RoadmapSupportVo>
}