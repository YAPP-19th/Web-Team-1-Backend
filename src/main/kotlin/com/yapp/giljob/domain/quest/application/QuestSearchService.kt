package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.user.application.UserMapper
import org.springframework.stereotype.Service

@Service
class QuestSearchService(
    private val questRepository: QuestRepository,

    private val questMapper: QuestMapper,
    private val userMapper: UserMapper
) {
    fun search(keyword: String, position: Position, size: Long, questId: Long?): List<QuestResponseDto> {
        val questList = questRepository.search(keyword, position, size, questId)
        return questList.map {
            questMapper.toDto(it, userMapper.toDto(it.quest.user, it.point))
        }
    }
}