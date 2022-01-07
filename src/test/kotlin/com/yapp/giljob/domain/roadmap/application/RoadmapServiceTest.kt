package com.yapp.giljob.domain.roadmap.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.roadmap.dao.RoadmapRepository
import com.yapp.giljob.domain.roadmap.dao.RoadmapScrapRepository
import com.yapp.giljob.domain.user.application.UserMapper
import com.yapp.giljob.domain.user.application.UserService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class RoadmapServiceTest {

    private lateinit var roadmapService: RoadmapService

    @MockK
    private lateinit var roadmapRepository: RoadmapRepository

    @MockK
    private lateinit var roadmapScrapRepository: RoadmapScrapRepository

    @MockK
    private lateinit var userService: UserService

    @MockK
    private lateinit var questService: QuestService

    @MockK
    private lateinit var roadmapMapper: RoadmapMapper

    @MockK
    private lateinit var userMapper: UserMapper

    private val roadmapId = 1L

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        roadmapService = RoadmapService(roadmapRepository, roadmapScrapRepository, userService, questService, roadmapMapper, userMapper)
    }

    @Test
    fun `자신이 작성한 로드맵이 아닌 경우 예외가 발생한다`() {
        // given
        every { roadmapRepository.findByIdOrNull(any()) } returns EntityFactory.testRoadmap()

        val nonWriter = User(
            id = 2L,
            socialId = "testSocialId",
            nickname = "testNickname",
            intro = "testIntro",
            position = Position.BACKEND
        )

        // when
        val exception =
            assertThrows(BusinessException::class.java) {
                roadmapService.deleteRoadmap(roadmapId, nonWriter)
            }

        // then
        Assertions.assertEquals(ErrorCode.CAN_NOT_DELETE_ROADMAP, exception.errorCode)
    }

    @Test
    fun `로드맵을 삭제한다`() {
        // given
        every { roadmapRepository.findByIdOrNull(any()) } returns EntityFactory.testRoadmap()
        every { roadmapRepository.delete(any()) } returns Unit
        val writer = EntityFactory.testUser()

        // when
        roadmapService.deleteRoadmap(roadmapId, writer)

        // then
        verify { roadmapRepository.delete(any()) }
    }

}