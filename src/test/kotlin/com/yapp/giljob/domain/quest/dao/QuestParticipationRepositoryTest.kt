package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.config.QuerydslTestConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(QuerydslTestConfig::class)
class QuestParticipationRepositoryTest @Autowired constructor(
    private val questRepository: QuestRepository,
    private val userRepository: UserRepository,
    private val questParticipationRepository: QuestParticipationRepository
){

    private val user  = EntityFactory.testUser()
    private val quest = EntityFactory.testQuest()

    @Test
    @Disabled
    fun `getQuestParticipationByQuestIdAndParticipantId 성공`() {
        val savedQuest = questRepository.save(quest)
        val savedUser = userRepository.save(user)
        questParticipationRepository.save(
            QuestParticipation(quest = savedQuest, participant = savedUser)
        )

        val questParticipation
         = questParticipationRepository.findByQuestIdAndParticipantId(savedQuest.id!!, savedUser.id!!)

        assertNotNull(questParticipation)
    }

    @Test
    fun `없는 퀘스트 찾으면 null 반환`() {
        val questParticipation
                = questParticipationRepository.findByQuestIdAndParticipantId(0L, 0L)

        assertNull(questParticipation)
    }
}