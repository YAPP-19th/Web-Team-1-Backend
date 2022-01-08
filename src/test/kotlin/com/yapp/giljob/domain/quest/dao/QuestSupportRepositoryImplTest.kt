package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.dto.QuestConditionDto
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.domain.user.domain.Ability
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.config.QuerydslTestConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest

@DataJpaTest
@Import(QuerydslTestConfig::class)
class QuestSupportRepositoryImplTest {

    @Autowired
    private lateinit var questRepository: QuestRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var abilityRepository: AbilityRepository

    private val questSaveSize = 5

    private lateinit var user: User

    private lateinit var questSaveList: MutableList<Quest>

    private var lastQuestId: Long = 0

    @BeforeEach
    fun setUp() {
        questSaveList = mutableListOf()
        user = userRepository.save(EntityFactory.testUser())

        // 백엔드 저장
        repeat(questSaveSize) {
            questSaveList.add(
                questRepository.save(
                    Quest(
                        name = "test quest",
                        user = user,
                        position = Position.BACKEND,
                        difficulty = 1,
                        thumbnail = "test.png",
                        detail = "test quest detail"
                    )
                )
            )
        }
    }

    @Nested
    inner class CursorBasedTest {
        @Test
        fun `page가 0인 경우 최신순으로 전체 리스트를 불러온다`() {
            // given
            lastQuestId = questSaveList[questSaveList.size - 1].id!!

            // when
            val questList =
                questRepository.getQuestList(
                    QuestConditionDto(position = Position.ALL),
                    PageRequest.of(0, questSaveSize)
                )

            // then
            assertEquals(questSaveSize, questList.size)
            assertEquals(lastQuestId, questList[0].quest.id)
            assertEquals(lastQuestId - 1L, questList[1].quest.id)
            assertEquals(lastQuestId - 2L, questList[2].quest.id)
        }
    }

    @Nested
    inner class AbilityTest {

        @Test
        fun `능력치가 존재하면 해당 point를 가져온다`() {
            // given
            val size = 1
            val point = 100L
            abilityRepository.save(Ability(user = user, position = Position.BACKEND, point = point))

            // when
            val questList =
                questRepository.getQuestList(
                    QuestConditionDto(position = Position.ALL),
                    PageRequest.of(0, size)
                )

            // then
            assertEquals(point, questList[0].point)
        }

        @Test
        fun `능력치가 존재하지 않으면 0을 반환한다`() {
            // given
            val size = 1

            // when
            val questList =
                questRepository.getQuestList(
                    QuestConditionDto(position = Position.ALL),
                    PageRequest.of(0, size)
                )

            // then
            assertEquals(0, questList[0].point)
        }
    }

    @Nested
    inner class PositionTest {

        @BeforeEach
        fun setUp() {
            questSaveList.add(
                questRepository.save(
                    Quest(
                        name = "test quest",
                        user = user,
                        position = Position.FRONTEND,
                        difficulty = 1,
                        thumbnail = "test.png",
                        detail = "test quest detail"
                    )
                )
            )
        }

        @Test
        fun `ALL 포지션 조회`() {
            // when
            val questList =
                questRepository.getQuestList(
                    QuestConditionDto(position = Position.ALL),
                    PageRequest.of(0, questSaveList.size)
                )

            // then
            assertEquals(6, questList.size)
        }

        @Test
        fun `BACKEND 포지션 조회`() {
            // when
            val questList =
                questRepository.getQuestList(
                    QuestConditionDto(position = Position.BACKEND),
                    PageRequest.of(0, questSaveList.size)
                )

            // then
            assertEquals(5, questList.size)
        }

        @Test
        fun `FRONTEND 포지션 조회`() {
            // when
            val questList =
                questRepository.getQuestList(
                    QuestConditionDto(position = Position.FRONTEND),
                    PageRequest.of(0, questSaveList.size)
                )

            // then
            assertEquals(1, questList.size)
        }
    }

    @Nested
    inner class QuestDetailTest {

        @Test
        fun `questDetailInfo 성공`() {
            lastQuestId = questSaveList[questSaveList.size - 1].id!!

            val questSupportVo = questRepository.findByQuestId(lastQuestId)

            assertEquals(questSupportVo!!.quest.id, lastQuestId)
        }

        @Test
        fun `존재하지 않는 퀘스트 상세 조회시 null 리턴`() {
            val questSupportVo = questRepository.findByQuestId(lastQuestId)

            assertNull(questSupportVo)
        }
    }
}