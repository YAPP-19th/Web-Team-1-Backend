package com.yapp.giljob.domain.roadmap.application

import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.roadmap.dao.RoadmapQuestRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.roadmap.domain.Roadmap
import com.yapp.giljob.domain.roadmap.domain.RoadmapScrapPK
import com.yapp.giljob.domain.roadmap.dto.request.RoadmapSaveRequestDto
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapDetailResponseDto
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.application.UserService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoadmapService(
    private val roadmapRepository: RoadmapRepository,
    private val roadmapScrapRepository: RoadmapScrapRepository,
    private val roadmapQuestRepository: RoadmapQuestRepository,

    private val userService: UserService,
    private val questService: QuestService,

    private val roadmapMapper: RoadmapMapper,
    private val userMapper: UserMapper
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
    fun deleteRoadmap(roadmapId: Long, user: User) {
        val roadmap = getRoadmap(roadmapId)
        if (roadmap.user != user) throw BusinessException(ErrorCode.CAN_NOT_DELETE_ROADMAP)
        roadmapRepository.delete(roadmap)
    }

    @Transactional
    fun saveRoadmap(roadmapSaveRequestDto: RoadmapSaveRequestDto, user: User) {
        val roadmap = Roadmap.of(roadmapSaveRequestDto, user)

        val questList = questService.convertToQuestList(roadmap, roadmapSaveRequestDto.questList)
        roadmap.questList.addAll(questList)

        roadmapQuestRepository.saveAll(questList)
        roadmapRepository.save(roadmap)
    }

    private fun getRoadmap(roadmapId: Long) =
        roadmapRepository.findByIdOrNull(roadmapId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)

    fun getRoadmapList(pageable: Pageable): List<RoadmapResponseDto>? {
        val roadmapVoList = roadmapRepository.findRoadmapList(pageable)
        return roadmapVoList.map {
            roadmapMapper.toDto(it.roadmap, userMapper.toDto(it.roadmap.user, it.point))
        }
    }
}