package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.domain.user.domain.Ability
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.EntityFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QuestSupportRepositoryImplTest {

    @Autowired
    private lateinit var questSupportRepository: QuestSupportRepositoryImpl

    @Autowired
    private lateinit var questRepository: QuestRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var abilityRepository: AbilityRepository

    private val lastQuestId = 5L

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = userRepository.save(EntityFactory.testUser())

        for (i in 1..lastQuestId) {
            questRepository.save(
                Quest(
                    id = i,
                    name = "test quest $i",
                    user = user,
                    position = Position.BACKEND,
                    difficulty = 1,
                    thumbnail = "test.png",
                    detail = "test quest detail"
                )
            )
        }
    }

    @AfterEach
    fun deleteAll() {
        abilityRepository.deleteAll()
    }

    @Test
    fun `cursorId가 null인 경우 전체 리스트에서 불러온다`() {
        // given
        val cursorId = null
        val size = 3L

        // when
        val questList = questSupportRepository.findByIdLessThanAndOrderByIdDesc(cursorId, size)

        // then
        assertEquals(size, questList.size.toLong())
        assertEquals(lastQuestId, questList[0].id)
        assertEquals(lastQuestId - 1, questList[1].id)
        assertEquals(lastQuestId - 2, questList[2].id)
    }

    @Test
    fun `cursorId가 null이 아닌 경우, cursorId - 1 퀘스트부터 최신순으로 리스트를 가져온다`() {
        // given
        val cursorId = 4L
        val size = 3L

        // when
        val questList = questSupportRepository.findByIdLessThanAndOrderByIdDesc(cursorId, size)

        // then
        assertEquals(size, questList.size.toLong())
        assertEquals(cursorId - 1, questList[0].id)
        assertEquals(cursorId - 2, questList[1].id)
        assertEquals(cursorId - 3, questList[2].id)
    }

    @Test
    fun `주어진 퀘스트 리스트 사이즈보다 더 큰 사이즈를 요구하면 리스트 사이즈만큼 가져온다`() {
        // given
        val cursorId = 4L
        val size = lastQuestId + 1

        // when
        val questList = questSupportRepository.findByIdLessThanAndOrderByIdDesc(cursorId, size)

        // then
        assertNotEquals(size, questList.size.toLong())
        assertEquals(cursorId - 1, questList.size.toLong())
    }

    @Test
    fun `능력치가 존재하면 해당 point를 가져온다`() {
        // given
        val size = 1L
        val point = 100
        abilityRepository.save(Ability(user = user, position = Position.BACKEND, point = point))

        // when: questId 가 1L인 퀘스트 조회
        val questList = questSupportRepository.findByIdLessThanAndOrderByIdDesc(2L, size)

        // then
        assertEquals(point, questList[0].point)
    }

    @Test
    fun `능력치가 존재하지 않으면 0을 반환한다`() {
        // given
        val size = 1L

        // when: questId 가 1L인 퀘스트 조회
        val questList = questSupportRepository.findByIdLessThanAndOrderByIdDesc(2L, size)

        // then
        assertEquals(0, questList[0].point)
    }
}