package com.yapp.giljob.domain.roadmap.dao

import com.yapp.giljob.domain.roadmap.domain.Roadmap
import org.springframework.data.jpa.repository.JpaRepository

interface RoadmapRepository: JpaRepository<Roadmap, Long>, RoadmapSupportRepository