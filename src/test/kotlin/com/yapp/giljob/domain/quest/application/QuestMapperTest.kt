package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class QuestMapperTest {

    private val questMapper = Mappers.getMapper(QuestMapper::class.java)

    @Test
    fun `toQuestResponse 테스트`() {
        // given
        val quest = EntityFactory.testQuest()
        val expectedQuestResponse = DtoFactory.testQuestResponse()

        // when
        val questResponse = questMapper.toDto(quest)

        // then
        assertEquals(expectedQuestResponse.id, questResponse.id)
        assertEquals(expectedQuestResponse.name, questResponse.name)
        assertEquals(expectedQuestResponse.position, questResponse.position)
        assertEquals(expectedQuestResponse.difficulty, questResponse.difficulty)
        assertEquals(expectedQuestResponse.thumbnail, questResponse.thumbnail)
        assertEquals(expectedQuestResponse.user.id, questResponse.user.id)
        assertEquals(expectedQuestResponse.user.nickname, questResponse.user.nickname)
    }
}