package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.quest.application.QuestMapper
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.subquest.dao.SubQuestParticipationRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserQuestServiceTest {

    private lateinit var userQuestService: UserQuestService

    @MockK
    private lateinit var questRepository: QuestRepository

    @MockK
    private lateinit var questParticipationRepository: QuestParticipationRepository

    @MockK
    private lateinit var subQuestParticipationRepository: SubQuestParticipationRepository

    @MockK
    private lateinit var questMapper: QuestMapper

    @MockK
    private lateinit var userMapper: UserMapper

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        userQuestService = UserQuestService(questRepository, questParticipationRepository, subQuestParticipationRepository, questMapper, userMapper)
    }

    @Test
    fun `9개의 서브퀘스트 중 3개를 완료하면 33%의 진행률이 나온다`() {
        // given
        val totalSubQuestCount = 9
        val subQuestCompletedCount = 3L

        // when
        val progress = userQuestService.calculateProgress(totalSubQuestCount, subQuestCompletedCount)

        // then
        assertEquals(33, progress)
    }
}
