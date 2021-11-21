package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.quest.domain.QuestParticipationPK
import com.yapp.giljob.domain.subquest.domain.SubQuest
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.config.QuerydslTestConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslTestConfig::class)
class QuestRepositoryTest @Autowired constructor(
    private val questRepository: QuestRepository,
    private val questParticipationRepository: QuestParticipationRepository,
    private val userRepository: UserRepository
) {

    private var userList = mutableListOf<User>()

    private var questList = mutableListOf<Quest>()

    @BeforeEach
    fun setUp() {
        for (i in 1..5L) {
            userList.add(
                userRepository.save(
                    User(socialId = "testSocialId", nickname = "testNickname", position = Position.BACKEND)
                )
            )

        }
        // 첫 번째 유저가 퀘스트들을 생성한다.
        for (i in 1..5L) {
            questList.add(
                questRepository.save(
                    Quest(name = "test quest", user = userList[0], position = Position.BACKEND, tagList = mutableListOf(), difficulty = 1, thumbnail = "test.png", detail = "test quest detail", subQuestList = mutableListOf(SubQuest(name = "sub quest 1"), SubQuest(name = "sub quest 2")))
                )
            )
        }

    }

    @Test
    fun `1명이 여러 개의 퀘스트에 참여하는 경우 조회 테스트`() {
        // given
        questParticipationRepository.save(QuestParticipation(QuestParticipationPK(userList[1].id!!, questList[0].id!!), questList[0], userList[1]))
        questParticipationRepository.save(QuestParticipation(QuestParticipationPK(userList[1].id!!, questList[1].id!!), questList[1], userList[1]))
        questParticipationRepository.save(QuestParticipation(QuestParticipationPK(userList[1].id!!, questList[2].id!!), questList[2], userList[1]))
        questParticipationRepository.save(QuestParticipation(QuestParticipationPK(userList[1].id!!, questList[3].id!!), questList[3], userList[1]))

        // when
        val totalParticipantCount = questParticipationRepository.countByParticipant()
        val onProgressQuestCount = questParticipationRepository.countByQuest()

        assertEquals(1L, totalParticipantCount)
        assertEquals(4L, onProgressQuestCount)
    }

    @Test
    fun `여러 명이 1개의 퀘스트에 참여하는 경우 조회 테스트`() {
        // given
        questParticipationRepository.save(QuestParticipation(QuestParticipationPK(userList[1].id!!, questList[0].id!!), questList[0], userList[1]))
        questParticipationRepository.save(QuestParticipation(QuestParticipationPK(userList[2].id!!, questList[0].id!!), questList[0], userList[2]))
        questParticipationRepository.save(QuestParticipation(QuestParticipationPK(userList[3].id!!, questList[0].id!!), questList[0], userList[3]))
        questParticipationRepository.save(QuestParticipation(QuestParticipationPK(userList[4].id!!, questList[0].id!!), questList[0], userList[4]))

        // when
        val totalParticipantCount = questParticipationRepository.countByParticipant()
        val onProgressQuestCount = questParticipationRepository.countByQuest()

        // then
        assertEquals(4L, totalParticipantCount)
        assertEquals(1L, onProgressQuestCount)
    }
}