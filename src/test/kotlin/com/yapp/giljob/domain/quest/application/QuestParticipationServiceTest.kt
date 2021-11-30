package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class sQuestParticipationServiceTest {

    private lateinit var questParticipationService: QuestParticipationService

    @MockK
    private lateinit var questRepository: QuestRepository

    @MockK
    private lateinit var questParticipationRepository: QuestParticipationRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        questParticipationService = QuestParticipationService(questRepository, questParticipationRepository)
    }

    private val questId = 1L
    private val user = EntityFactory.testUser()
    private val quest = EntityFactory.testQuest()

    @Test
    fun `존재하지 않은 퀘스트에 참여하면 예외가 발생한다`() {
        // given
        every { questRepository.findByIdOrNull(any()) } returns null

        // when
        val exception =
            assertThrows(BusinessException::class.java) {
                questParticipationService.participateQuest(questId, user)
            }

        // then
        assertEquals(ErrorCode.ENTITY_NOT_FOUND, exception.errorCode)
    }

    @Test
    fun `이미 참여한 퀘스트에 참여하면 예외가 발생한다`() {
        // given
        val user = User(
            id = quest.user.id!! + 1,
            socialId = "testSocialId",
            nickname = "testNickname",
            position = Position.BACKEND
        )
        every { questRepository.findByIdOrNull(any()) } returns quest
        every { questParticipationRepository.existsById(any()) } returns true
        every { questParticipationRepository.save(any()) } returns EntityFactory.testQuestParticipation()

        // when
        val exception =
            assertThrows(BusinessException::class.java) {
                questParticipationService.participateQuest(questId, user)
            }

        // then
        assertEquals(ErrorCode.ALREADY_PARTICIPATED_QUEST, exception.errorCode)
    }

    @Test
    fun `퀘스트에 참여한다`() {
        // given
        val user = User(
            id = quest.user.id!! + 1,
            socialId = "testSocialId",
            nickname = "testNickname",
            position = Position.BACKEND
        )
        every { questRepository.findByIdOrNull(any()) } returns quest
        every { questParticipationRepository.existsById(any()) } returns false
        every { questParticipationRepository.save(any()) } returns EntityFactory.testQuestParticipation()

        // when
        questParticipationService.participateQuest(questId, user)

        // then
        verify { questParticipationRepository.save(any()) }
    }
}