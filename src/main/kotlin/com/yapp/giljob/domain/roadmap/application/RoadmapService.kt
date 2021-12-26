package com.yapp.giljob.domain.roadmap.application

import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.roadmap.domain.RoadmapScrapPK
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapDetailResponseDto
import com.yapp.giljob.domain.user.application.UserService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.roadmap.domain.Roadmap
import com.yapp.giljob.domain.roadmap.dto.request.RoadmapSaveRequestDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Service
class RoadmapService(
    private val roadmapRepository: RoadmapRepository,
    private val roadmapScrapRepository: RoadmapScrapRepository,

    private val userService: UserService,
    private val questService: QuestService,

    private val roadmapMapper: RoadmapMapper
) {
    fun getRoadmapDetail(roadmapId: Long, user: User): RoadmapDetailResponseDto {
        val roadmap = roadmapRepository.findByIdOrNull(roadmapId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)

        return roadmapMapper.toDto(
            roadmap,
            userService.getUserInfo(roadmap.user),
            roadmap.questList.map { it.quest },
            roadmapScrapRepository.existsById(RoadmapScrapPK(roadmapId, user.id!!))
        )
    }

    @Transactional
    fun saveRoadmap(roadmapSaveRequestDto: RoadmapSaveRequestDto, user: User) {
        val roadmap = Roadmap.of(roadmapSaveRequestDto, user)

        val questList = questService.convertToQuestList(roadmap, roadmapSaveRequestDto.questList)
        roadmap.questList.addAll(questList)

        roadmapRepository.save(roadmap)
    }
}