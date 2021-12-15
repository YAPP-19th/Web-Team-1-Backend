package com.yapp.giljob.domain.roadmap.application

import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.roadmap.domain.RoadmapScrap
import com.yapp.giljob.domain.roadmap.domain.RoadmapScrapPK
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RoadmapScrapService(
    private val roadmapRepository: RoadmapRepository,
    private val roadmapScrapRepository: RoadmapScrapRepository
) {
    fun scrap(roadmapId: Long, user: User) {
        val roadmap = roadmapRepository.findByIdOrNull(roadmapId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)

        val roadmapScrapPK = RoadmapScrapPK(user.id!!, roadmapId)
        if (roadmapScrapRepository.existsById(roadmapScrapPK)) throw BusinessException(ErrorCode.ALREADY_SCRAPED_ROADMAP)

        roadmapScrapRepository.save(RoadmapScrap(roadmapScrapPK, roadmap, user))
    }
}