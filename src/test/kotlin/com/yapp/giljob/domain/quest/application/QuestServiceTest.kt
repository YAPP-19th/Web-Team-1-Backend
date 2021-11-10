package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.mapper.QuestMapper
import com.yapp.giljob.domain.tag.dao.TagRepository
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class QuestServiceTest {

    private lateinit var questService: QuestService

    @MockK
    private lateinit var questRepository: QuestRepository

    @MockK
    private lateinit var tagRepository: TagRepository

    @MockK
    private lateinit var questMapper: QuestMapper

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        questService = QuestService(questRepository, tagRepository, questMapper)
    }

    @Nested
    inner class Tag {
        private val user = EntityFactory.testUser()
        private val quest = EntityFactory.testQuest()

        private val questRequest = DtoFactory.testQuestRequest()

        private val tag = EntityFactory.testTag()

        @Test
        fun `이미 저장된 태그 저장되는지 테스트`() {
            // given
            every { tagRepository.findByName(any()) } returns tag
            every { questRepository.save(any()) } returns quest

            every { questMapper.toEntity(any(), any()) } returns quest

            // when
            val savedQuest = questService.saveQuest(questRequest, user)

            // them
            assertEquals(tag.id, savedQuest.tagList[0].id.tagId)
            assertEquals(quest.id, savedQuest.tagList[0].id.questId)
            assertEquals(questRequest.tagList.size, savedQuest.tagList.size)
        }

        @Test
        fun `새로 저장된 태그 저장되는지 테스트`() {
            // given
            every { tagRepository.findByName(any()) } returns null
            every { tagRepository.save(any()) } returns tag
            every { questRepository.save(any()) } returns quest

            every { questMapper.toEntity(any(), any()) } returns quest

            // when
            val savedQuest = questService.saveQuest(questRequest, user)

            // then
            assertEquals(tag.id, savedQuest.tagList[0].id.tagId)
            assertEquals(quest.id, savedQuest.tagList[0].id.questId)
            assertEquals(questRequest.tagList.size, savedQuest.tagList.size)
        }
    }
}