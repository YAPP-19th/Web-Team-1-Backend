package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.roadmap.application.RoadmapMapper
import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import com.yapp.giljob.global.common.dto.ListResponseDto
import org.springframework.data.domain.Pageable
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
    fun getScrapRoadmapListByUser(userId: Long, pageable: Pageable): ListResponseDto<RoadmapResponseDto> {
        val scrapRoadmapList = roadmapScrapRepository.findByUserId(userId, pageable).map {
            roadmapMapper.toDto(it.roadmap, userMapper.toDto(it.roadmap.user, it.point))
        }

        return ListResponseDto(
            totalCount = scrapRoadmapList.size.toLong(),
            contentList = scrapRoadmapList
        )
    }

    fun getRoadmapListByUser(userId: Long, pageable: Pageable): ListResponseDto<RoadmapResponseDto> {
        val roadmapList = roadmapRepository.getRoadmapListByUser(userId, pageable).map {
            roadmapMapper.toDto(it.roadmap, userMapper.toDto(it.roadmap.user, it.point))
        }

        return ListResponseDto(
            totalCount = roadmapList.size.toLong(),
            contentList = roadmapList
        )
    }
}