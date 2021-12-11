package com.yapp.giljob.domain.roadmap.dao

import com.yapp.giljob.domain.roadmap.domain.RoadmapScrap
import com.yapp.giljob.domain.roadmap.domain.RoadmapScrapPK
import org.springframework.data.jpa.repository.JpaRepository

interface RoadmapScrapRepository: JpaRepository<RoadmapScrap, RoadmapScrapPK>