package com.yapp.giljob.domain.subquest.application

import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import com.yapp.giljob.domain.subquest.dao.SubQuestRepository
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

class SubQuestParticipationServiceTest {

    private lateinit var subQuestParticipationService: SubQuestParticipationService

    @MockK
    private lateinit var subQuestRepository: SubQuestRepository

    @MockK
    private lateinit var subQuestParticipationRepository: SubQuestParticipationRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        subQuestParticipationService = SubQuestParticipationService(subQuestRepository, subQuestParticipationRepository)
    }

    private val subQuestId = 3L
    private val user = EntityFactory.testUser()

    @Test
    fun `존재하지 않은 서브 퀘스트를 완료하면 예외가 발생한다`() {
        // given
        every { subQuestRepository.findByIdOrNull(any()) } returns null

        // when
        val exception =
            assertThrows(BusinessException::class.java) {
                subQuestParticipationService.completeSubQuest(subQuestId, user)
            }

        // then
        assertEquals(ErrorCode.ENTITY_NOT_FOUND, exception.errorCode)
    }

    @Test
    fun `이미 완료된 서브퀘스트를 완료하면 예외가 발생한다`() {
        // given
        every { subQuestRepository.findByIdOrNull(any()) } returns EntityFactory.testSubQuest()
        every { subQuestParticipationRepository.findByIdOrNull(any()) } returns EntityFactory.testSubQuestParticipation()

        // when
        val exception =
            assertThrows(BusinessException::class.java) {
                subQuestParticipationService.completeSubQuest(subQuestId, user)
            }

        // then
        assertEquals(ErrorCode.ALREADY_COMPLETED_SUBQUEST, exception.errorCode)
    }

    @Test
    fun `서브 퀘스트를 완료한다`() {
        // given
        every { subQuestRepository.findByIdOrNull(any()) } returns EntityFactory.testSubQuest()
        every { subQuestParticipationRepository.findByIdOrNull(any()) } returns null
        every { subQuestParticipationRepository.save(any()) } returns EntityFactory.testSubQuestParticipation()

        // when
        subQuestParticipationService.completeSubQuest(subQuestId, user)

        // then
        verify { subQuestParticipationRepository.save(any()) }
    }

}