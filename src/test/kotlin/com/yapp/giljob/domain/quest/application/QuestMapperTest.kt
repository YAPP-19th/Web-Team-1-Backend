package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.vo.QuestSupportVo
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class QuestMapperTest {

    private val questMapper = Mappers.getMapper(QuestMapper::class.java)
    private val userMapper = Mappers.getMapper(UserMapper::class.java)

    @Test
    fun `toQuestResponse 테스트`() {
        // given
        val testQuestSupportDto = QuestSupportVo(
            quest = EntityFactory.testQuest(),
            point = 100,
            participantCount = 1L
        )
        val expectedQuestResponse = DtoFactory.testQuestDetailResponse()

        // when
        val questResponse = questMapper.toDto(testQuestSupportDto, userMapper.toDto(testQuestSupportDto.quest.user, testQuestSupportDto.point))

        // then
        assertEquals(expectedQuestResponse.id, questResponse.id)
        assertEquals(expectedQuestResponse.name, questResponse.name)
        assertEquals(expectedQuestResponse.position, questResponse.position)
        assertEquals(expectedQuestResponse.difficulty, questResponse.difficulty)
        assertEquals(expectedQuestResponse.thumbnail, questResponse.thumbnail)
        assertEquals(expectedQuestResponse.writer.point, questResponse.writer.point)
        assertEquals(expectedQuestResponse.writer.id, questResponse.writer.id)
        assertEquals(expectedQuestResponse.writer.nickname, questResponse.writer.nickname)
    }
}