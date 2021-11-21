package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestMapper
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.user.dao.UserMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserQuestService(
    private val questRepository: QuestRepository,

    private val questMapper: QuestMapper,
    private val userMapper: UserMapper
) {
    @Transactional(readOnly = true)
    fun getQuestListByUser(userId: Long, questId: Long?, position: Position, size: Long): List<QuestResponseDto> {
        val questList = questRepository.findByUserIdAndIdLessThanAndOrderByIdDesc(userId, questId, position, size)

        return questList.map {
            questMapper.toDto(it, userMapper.toDto(it.user, it.point))
        }
    }
}