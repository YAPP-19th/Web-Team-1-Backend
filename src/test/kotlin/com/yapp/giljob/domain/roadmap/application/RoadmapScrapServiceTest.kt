package com.yapp.giljob.domain.roadmap.application

import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
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

class RoadmapScrapServiceTest {

    private lateinit var roadmapScrapService: RoadmapScrapService

    @MockK
    private lateinit var roadmapRepository: RoadmapRepository

    @MockK
    private lateinit var roadmapScrapRepository: RoadmapScrapRepository

    private val roadmapId = 1L
    private val user = EntityFactory.testUser()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        roadmapScrapService = RoadmapScrapService(roadmapRepository, roadmapScrapRepository)
    }

    @Test
    fun `존재하지 않은 로드맵을 스크랩하면 예외가 발생한다`() {
        // given
        every { roadmapRepository.findByIdOrNull(any()) } returns null

        // when
        val exception =
            assertThrows(BusinessException::class.java) {
                roadmapScrapService.scrap(roadmapId, user)
            }

        // then
        assertEquals(ErrorCode.ENTITY_NOT_FOUND, exception.errorCode)
    }

    @Test
    fun `이미 스크랩한 로드맵을 스크랩하면 예외가 발생한다`() {
        // given
        every { roadmapRepository.findByIdOrNull(any()) } returns EntityFactory.testRoadmap()
        every { roadmapScrapRepository.existsById(any()) } returns true

        // when
        val exception =
            assertThrows(BusinessException::class.java) {
                roadmapScrapService.scrap(roadmapId, user)
            }

        // then
        assertEquals(ErrorCode.ALREADY_SCRAPED_ROADMAP, exception.errorCode)
    }

    @Test
    fun `로드맵을 스크랩한다`() {
        // given
        every { roadmapRepository.findByIdOrNull(any()) } returns EntityFactory.testRoadmap()
        every { roadmapScrapRepository.existsById(any()) } returns false
        every { roadmapScrapRepository.save(any()) } returns EntityFactory.testRoadmapScrap()

        // when
        roadmapScrapService.scrap(roadmapId, user)

        // then
        verify { roadmapScrapRepository.save(any()) }
    }
}