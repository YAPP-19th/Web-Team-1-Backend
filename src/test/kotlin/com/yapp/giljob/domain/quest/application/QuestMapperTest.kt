package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.user.dao.UserMapper
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class QuestMapperTest {

    private val questMapper = Mappers.getMapper(QuestMapper::class.java)

    // TODO UserMapperTest 로 빼야 함
    private val userMapper = Mappers.getMapper(UserMapper::class.java)

    @Test
    fun `toQuestResponse 테스트`() {
        // given
        val testQuestSupportDto = QuestSupportVo(
            quest = EntityFactory.testQuest(),
            point = 100,
            participantCount = 100
        )
        val expectedQuestResponse = DtoFactory.testQuestResponse()

        // when
        val questResponse = questMapper.toDto(testQuestSupportDto, userMapper.toDto(testQuestSupportDto.quest.user, testQuestSupportDto.point))

        // then
        assertEquals(expectedQuestResponse.id, questResponse.id)
        assertEquals(expectedQuestResponse.name, questResponse.name)
        assertEquals(expectedQuestResponse.position, questResponse.position)
        assertEquals(expectedQuestResponse.difficulty, questResponse.difficulty)
        assertEquals(expectedQuestResponse.thumbnail, questResponse.thumbnail)
        assertEquals(expectedQuestResponse.participantCount, questResponse.participantCount)
        assertEquals(expectedQuestResponse.user.point, questResponse.user.point)
        assertEquals(expectedQuestResponse.user.id, questResponse.user.id)
        assertEquals(expectedQuestResponse.user.nickname, questResponse.user.nickname)
    }
}