package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.config.QuerydslTestConfig
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
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
    @Test
    fun `getQuestParticipationByQuestIdAndParticipantId 성공`() {

        val user = User(socialId = "socialId", nickname = "nickname", intro = "intro", position = Position.FRONTEND)
        val quest = Quest(user = user, isRealQuest = false, name = "quest", position = Position.BACKEND)

        val savedUser = userRepository.save(user)
        val savedQuest = questRepository.save(quest)
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