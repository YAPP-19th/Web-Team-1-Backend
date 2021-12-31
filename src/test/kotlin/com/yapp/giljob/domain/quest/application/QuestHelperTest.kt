package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.config.QuerydslTestConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(QuerydslTestConfig::class)
class QuestHelperTest {

    @Autowired
    private lateinit var questRepository: QuestRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var questParticipationRepository: QuestParticipationRepository

    private var quest = EntityFactory.testQuest()
    private val user = EntityFactory.testUser()

    @Test
    fun `퀘스트에 참여자가 없을 경우 0을 리턴한다`() {
        val cnt = QuestHelper.countParticipantsByQuestId(questRepository, quest.id!!)
        assertEquals(cnt, 0)
    }

    @Test
    fun `퀘스트 참여자가 한명일 경우 1을 리턴한다`() {
        val savedUser = userRepository.save(user)
        quest.user = savedUser
        val savedQuest = questRepository.save(quest)
        questParticipationRepository.save(QuestParticipation(quest = savedQuest, participant =  savedUser))

        val cnt = QuestHelper.countParticipantsByQuestId(questRepository, savedQuest.id!!)

        assertEquals(cnt, 1)
    }
}