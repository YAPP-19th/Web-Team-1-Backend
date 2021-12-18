package com.yapp.giljob.domain.roadmap.application

import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.roadmap.domain.RoadmapScrapPK
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RoadmapService(
    private val roadmapRepository: RoadmapRepository,
    private val roadmapScrapRepository: RoadmapScrapRepository,

    private val roadmapMapper: RoadmapMapper
) {
    fun getRoadmap(roadmapId: Long, userId: Long): RoadmapResponseDto {
        val roadmap = roadmapRepository.findByIdOrNull(roadmapId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        return roadmapMapper.toDto(
            roadmap,
            roadmap.questList.map { it.quest },
            roadmapScrapRepository.existsById(RoadmapScrapPK(roadmapId, userId))
        )
    }
}