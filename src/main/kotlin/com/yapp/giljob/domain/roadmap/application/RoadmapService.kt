package com.yapp.giljob.domain.roadmap.application

import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.roadmap.domain.RoadmapScrapPK
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapDetailResponseDto
import com.yapp.giljob.domain.user.application.UserService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoadmapService(
    private val roadmapRepository: RoadmapRepository,
    private val roadmapScrapRepository: RoadmapScrapRepository,

    private val userService: UserService,

    private val roadmapMapper: RoadmapMapper
) {
    fun getRoadmapDetail(roadmapId: Long, user: User): RoadmapDetailResponseDto {
        val roadmap = getRoadmap(roadmapId)

        return roadmapMapper.toDto(
            roadmap,
            userService.getUserInfo(roadmap.user),
            roadmap.questList.map { it.quest },
            roadmapScrapRepository.existsById(RoadmapScrapPK(roadmapId, user.id!!))
        )
    }

    @Transactional
    fun delete(roadmapId: Long, user: User) {
        val roadmap = getRoadmap(roadmapId)
        if (roadmap.user != user) throw BusinessException(ErrorCode.CAN_NOT_DELETE_ROADMAP)
        roadmapRepository.delete(roadmap)
    }

    private fun getRoadmap(roadmapId: Long) =
        roadmapRepository.findByIdOrNull(roadmapId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
}