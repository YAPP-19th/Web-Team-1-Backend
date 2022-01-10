package com.yapp.giljob.domain.roadmap.dao

import com.yapp.giljob.domain.roadmap.vo.RoadmapSupportVo
import org.springframework.data.domain.Pageable

interface RoadmapSupportRepository {
    fun getRoadmapListByUser(userId: Long, pageable: Pageable): List<RoadmapSupportVo>

    fun findRoadmapList(size: Long): List<RoadmapSupportVo>
}