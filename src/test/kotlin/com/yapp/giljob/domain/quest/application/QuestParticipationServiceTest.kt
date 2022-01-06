package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class QuestParticipationServiceTest {

    @InjectMockKs
    private lateinit var questParticipationService: QuestParticipationService

    @MockK
    private lateinit var questRepository: QuestRepository

    @MockK
    private lateinit var questParticipationRepository: QuestParticipationRepository

    @MockK
    private lateinit var abilityRepository: AbilityRepository

    @MockK
    private lateinit var subQuestService: SubQuestService

    @MockK
    private lateinit var userMapper: UserMapper

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        questParticipationService = QuestParticipationService(questRepository, questParticipationRepository, abilityRepository, subQuestService, userMapper)
    }

    private val questId = 1L
    private val user = EntityFactory.testUser()
    private val quest = EntityFactory.testQuest()
    private val questParticipation = EntityFactory.testQuestParticipation()

    private val questReviewCreateRequestDto = DtoFactory.testQuestReviewCreateRequest()

    @Nested
    inner class QuestParticipation {

        @Test
        fun `존재하지 않은 퀘스트에 참여하면 예외가 발생한다`() {
            // given
            every { questRepository.findByIdOrNull(any())} returns null
            every { questParticipationRepository.findByQuestIdAndParticipantId(any(), any()) } returns null

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
                intro = "testIntro",
                position = Position.BACKEND
            )
            every { questRepository.findByIdOrNull(any()) } returns quest
            every { questParticipationRepository.existsByQuestIdAndParticipantId(any(), any()) } returns true
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
                intro = "testIntro",
                position = Position.BACKEND
            )
            every { questRepository.findByIdOrNull(any()) } returns quest
            every { questParticipationRepository.existsByQuestIdAndParticipantId(any(), any()) } returns false
            every { questParticipationRepository.save(any()) } returns EntityFactory.testQuestParticipation()

            // when
            questParticipationService.participateQuest(questId, user)

            // then
            verify { questParticipationRepository.save(any()) }
        }
    }

    @Nested
    inner class QuestComplete {

        private val questParticipation = EntityFactory.testQuestParticipation()

        @Test
        fun `참여하지 않은 퀘스트를 완료하면 예외가 발생한다`() {
            // given
            every { questParticipationRepository.findByQuestIdAndParticipantId(any(), any()) } returns null
            // when
            val exception =
                assertThrows(BusinessException::class.java) {
                    questParticipationService.completeQuest(questId, user)
                }

            // then
            assertEquals(ErrorCode.ENTITY_NOT_FOUND, exception.errorCode)
        }

        @Test
        fun `이미 완료된 퀘스트를 완료하면 예외가 발생한다`() {
            // given
            every { questParticipationRepository.findByQuestIdAndParticipantId(any(), any()) } returns questParticipation.also { it.isCompleted = true }

            // when
            val exception =
                assertThrows(BusinessException::class.java) {
                    questParticipationService.completeQuest(questId, user)
                }

            // then
            assertEquals(ErrorCode.ALREADY_COMPLETED_QUEST, exception.errorCode)
        }

        @Test
        fun `완료되지 않은 서브 퀘스트가 존재할 때 퀘스트를 완료하면 예외가 발생한다`() {
            // given
            every { questParticipationRepository.findByQuestIdAndParticipantId(any(), any()) } returns questParticipation
            every { subQuestService.countCompletedSubQuest(any(), any()) } returns 1

            // when
            val exception =
                assertThrows(BusinessException::class.java) {
                    questParticipationService.completeQuest(questId, user)
                }

            // then
            assertEquals(ErrorCode.NOT_COMPLETED_QUEST, exception.errorCode)
        }

        @Test
        fun `퀘스트를 완료한다`() {
            // given
            every { questParticipationRepository.findByQuestIdAndParticipantId(any(), any()) } returns questParticipation.also { it.isCompleted = true }

            // when
            val exception =
                assertThrows(BusinessException::class.java) {
                    questParticipationService.completeQuest(questId, user)
                }

            // then
            assertEquals(ErrorCode.ALREADY_COMPLETED_QUEST, exception.errorCode)
        }
    }

    @Test
    fun  `퀘스트 리뷰 작성 성공`() {
        questParticipation.isCompleted = true
        every { questParticipationRepository.findByQuestIdAndParticipantId(any(), any()) } returns questParticipation
        every { questParticipationRepository.save(any()) } returns questParticipation

        questParticipationService.createQuestReview(1L, questReviewCreateRequestDto, user)

        verify { questParticipationRepository.save(any()) }
    }

    @Test
    fun `존재하지 않는 퀘스트에 리뷰 작성하면 에러`() {
        every { questParticipationRepository.findByQuestIdAndParticipantId(any(), any()) } returns null

        val exception =
            assertThrows(BusinessException::class.java) {
                questParticipationService.createQuestReview(1L, questReviewCreateRequestDto, user)
            }

        assertEquals(ErrorCode.ENTITY_NOT_FOUND, exception.errorCode)
    }

    @Test
    fun `완료하지 않은 퀘스트에 리뷰 작성하면 에러`() {
        questParticipation.isCompleted = false
        every { questParticipationRepository.findByQuestIdAndParticipantId(any(), any()) } returns questParticipation

        val exception =
            assertThrows(BusinessException::class.java) {
                questParticipationService.createQuestReview(1L, questReviewCreateRequestDto, user)
            }

        assertEquals(ErrorCode.CAN_NOT_CREATE_QUEST_REVIEW, exception.errorCode)
    }
}