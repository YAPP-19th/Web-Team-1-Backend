package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.roadmap.application.RoadmapMapper
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRoadmapService(
    private val roadmapScrapRepository: RoadmapScrapRepository,
    private val roadmapMapper: RoadmapMapper
) {
    @Transactional(readOnly = true)
    fun getScrapRoadmapListByUser(userId: Long, roadmapId: Long?, size: Long): List<RoadmapResponseDto> {
         return roadmapScrapRepository.findByUserId(userId, roadmapId, size). map {
             RoadmapResponseDto(
                 id = it.roadmapId,
                 name = it.name,
                 position = it.position,
                 userId = it.userId,
                 nickname = it.nickname,
                 point = it.point
             )
        }
    }
}