package com.yapp.giljob.domain.roadmap.dao

import com.yapp.giljob.domain.roadmap.domain.RoadmapQuest
import org.springframework.data.jpa.repository.JpaRepository

interface RoadmapQuestRepository: JpaRepository<RoadmapQuest, Long>