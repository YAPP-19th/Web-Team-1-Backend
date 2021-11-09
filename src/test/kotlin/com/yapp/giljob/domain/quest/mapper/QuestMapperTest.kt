package com.yapp.giljob.domain.quest.mapper

import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QuestMapperTest(
    @Autowired
    val questMapper: QuestMapper
) {
    @Test
    fun `toEntity 테스트`() {
        // given
        val questRequest = DtoFactory.teatQuestRequest()
        val user = EntityFactory.testUser()
        // when
        val quest = questMapper.toEntity(questRequest, user)
        // then
        assertEquals(questRequest.detail, quest.detail)
        assertEquals(questRequest.difficulty, quest.difficulty)
        assertEquals(questRequest.thumbnail, quest.thumbnail)
        assertEquals(null , quest.tagList)
        assertEquals(null, quest.subQuestList[0].quest)
        assertEquals(questRequest.subQuestList[0].name, quest.subQuestList[0].name)
        assertEquals(user, quest.user)
    }
}