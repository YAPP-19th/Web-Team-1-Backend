package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.roadmap.application.RoadmapMapper
import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRoadmapService(
    private val roadmapScrapRepository: RoadmapScrapRepository,
    private val roadmapRepository: RoadmapRepository,

    private val roadmapMapper: RoadmapMapper,
    private val userMapper: UserMapper,
) {
    @Transactional(readOnly = true)
    fun getScrapRoadmapListByUser(userId: Long, roadmapId: Long?, size: Long): List<RoadmapResponseDto> {
        return roadmapScrapRepository.findByUserId(userId, roadmapId, size).map {
            roadmapMapper.toDto(it.roadmap, userMapper.toDto(it.roadmap.user, it.point))
        }
    }

    fun getRoadmapListByUser(userId: Long, cursor: Long?, size: Long): List<RoadmapResponseDto> {
        val roadmapVoList = roadmapRepository.findByUserAndIdLessThanAndOrderByIdDesc(userId, cursor, size)
        return roadmapVoList.map {
            roadmapMapper.toDto(it.roadmap, userMapper.toDto(it.roadmap.user, it.point))
        }
    }
}