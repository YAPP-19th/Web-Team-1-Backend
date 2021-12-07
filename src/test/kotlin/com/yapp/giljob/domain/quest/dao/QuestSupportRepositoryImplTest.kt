package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.domain.user.domain.Ability
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.EntityFactory
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Disabled
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @BeforeAll
    fun setUp() {
        questSaveList = mutableListOf()
        user = userRepository.save(EntityFactory.testUser())

        // 백엔드 저장
        repeat(questSaveSize - 1) {
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

        // 프론트엔드 저장
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

    @AfterAll
    fun tearDown() {
        questRepository.deleteAll()
    }

    @Test
    fun name() {

    }

    @Nested
    inner class CursorBasedTest {
        @Test
        fun `cursorId가 null인 경우 최신순으로 전체 리스트를 불러온다`() {
            // given
            val cursorId = null
            lastQuestId = questSaveList[questSaveList.size - 1].id!!

            // when
            val questList =
                questRepository.findByIdLessThanAndOrderByIdDesc(
                    questId = cursorId,
                    position = Position.ALL,
                    size = questSaveSize.toLong()
                )

            // then
            assertEquals(questSaveSize, questList.size)
            assertEquals(lastQuestId, questList[0].quest.id)
            assertEquals(lastQuestId - 1L, questList[1].quest.id)
            assertEquals(lastQuestId - 2L, questList[2].quest.id)
        }

        @Test
        fun `cursorId가 null이 아닌 경우, cursorId - 1 퀘스트부터 최신순으로 리스트를 가져온다`() {
            // given
            val cursorId = questSaveList[questSaveList.size - 1].id!! - 1L
            val size = 3L

            // when
            val questList = questRepository.findByIdLessThanAndOrderByIdDesc(
                questId = cursorId,
                position = Position.ALL,
                size = size
            )

            // then
            assertEquals(size, questList.size.toLong())
            assertEquals(cursorId - 1L, questList[0].quest.id)
            assertEquals(cursorId - 2L, questList[1].quest.id)
            assertEquals(cursorId - 3L, questList[2].quest.id)
        }

        @Test
        fun `주어진 퀘스트 리스트 사이즈보다 더 큰 사이즈를 요구하면 리스트 사이즈만큼 가져온다`() {
            // given
            val size = questSaveList.size + 1L
            lastQuestId = questSaveList[questSaveList.size - 1].id!!

            // when
            val questList = questRepository.findByIdLessThanAndOrderByIdDesc(
                questId = lastQuestId,
                position = Position.ALL,
                size = size
            )

            // then
            assertNotEquals(questSaveList.size, questList.size.toLong())
        }
    }

    @Nested
    inner class AbilityTest {

        @Test
        fun `능력치가 존재하면 해당 point를 가져온다`() {
            // given
            val size = 1L
            val point = 100L
            abilityRepository.save(Ability(user = user, position = Position.BACKEND, point = point))

            // when
            val questList =
                questRepository.findByIdLessThanAndOrderByIdDesc(
                    questId = questSaveList[questSaveList.size - 1].id,
                    position = Position.ALL,
                    size = size
                )

            // then
            assertEquals(point, questList[0].point)
        }

        @Test
        fun `능력치가 존재하지 않으면 0을 반환한다`() {
            // given
            val size = 1L

            // when
            val questList =
                questRepository.findByIdLessThanAndOrderByIdDesc(
                    questId = questSaveList[questSaveList.size - 1].id,
                    position = Position.ALL,
                    size = size
                )

            // then
            assertEquals(0, questList[0].point)
        }
    }

    @Nested
    inner class PositionTest {

        @Test
        fun `ALL 포지션 조회`() {
            // when
            lastQuestId = questSaveList[questSaveList.size - 1].id!!

            val questList = questRepository.findByIdLessThanAndOrderByIdDesc(
                questId = lastQuestId + 1L,
                position = Position.ALL,
                size = questSaveList.size.toLong()
            )

            // then
            assertEquals(5, questList.size)
        }

        @Test
        fun `BACKEND 포지션 조회`() {
            // when
            lastQuestId = questSaveList[questSaveList.size - 1].id!!

            println(lastQuestId)

            val questList = questRepository.findByIdLessThanAndOrderByIdDesc(
                questId = lastQuestId + 1L,
                position = Position.BACKEND,
                size = questSaveList.size.toLong()
            )

            // then
            assertEquals(4, questList.size)
        }

        @Test
        fun `FRONTEND 포지션 조회`() {
            // when
            lastQuestId = questSaveList[questSaveList.size - 1].id!!

            val questList = questRepository.findByIdLessThanAndOrderByIdDesc(
                questId = lastQuestId + 1L,
                position = Position.FRONTEND,
                size = questSaveList.size.toLong()
            )

            // then
            assertEquals(1, questList.size)
        }
    }
}