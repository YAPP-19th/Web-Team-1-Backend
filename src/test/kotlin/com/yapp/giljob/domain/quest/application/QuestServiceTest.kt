package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import com.yapp.giljob.domain.tag.application.TagService
import com.yapp.giljob.domain.tag.domain.QuestTag
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.application.UserQuestService
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class QuestServiceTest {

    private lateinit var questService: QuestService

    @MockK
    private lateinit var questRepository: QuestRepository

    @MockK
    private lateinit var subQuestParticipationRepository: SubQuestParticipationRepository

    @MockK
    private lateinit var subQuestService: SubQuestService

    @MockK
    private lateinit var userQuestService: UserQuestService

    @MockK
    private lateinit var tagService: TagService

    @MockK
    private lateinit var questMapper: QuestMapper

    @MockK
    private lateinit var userMapper: UserMapper

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        questService = QuestService(questRepository, subQuestParticipationRepository, subQuestService, tagService, userQuestService, questMapper, userMapper)
    }

    private val user = EntityFactory.testUser()
    private var quest = EntityFactory.testQuest()
    private val tag = EntityFactory.testTag()
    private val questTag = QuestTag(quest = quest, tag = tag)

    private val questRequest = DtoFactory.testQuestRequest()

    @Test
    fun `저장 테스트`() {
        // given
        quest.tagList = mutableListOf(questTag)
        every { subQuestService.convertToSubQuestList(any(), any()) } returns quest.subQuestList
        every { tagService.convertToQuestTagList(any(), any()) } returns listOf(questTag)
        every { questRepository.save(any()) } returns quest

        // when
        val savedQuest = questService.saveQuest(questRequest, user)

        // then
        assertEquals(1, savedQuest.tagList.size)
        assertEquals(quest.subQuestList.size, savedQuest.subQuestList.size)
    }
}