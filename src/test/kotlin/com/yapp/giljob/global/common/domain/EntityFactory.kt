package com.yapp.giljob.global.common.domain

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.quest.domain.QuestParticipationPK
import com.yapp.giljob.domain.subquest.domain.SubQuest
import com.yapp.giljob.domain.tag.domain.Tag
import com.yapp.giljob.domain.user.domain.User

class EntityFactory {
    companion object {
        fun testUser() = User(
            id = 1L,
            socialId = "testSocialId",
            nickname = "testNickname",
            position = Position.BACKEND
        )
        fun testQuest() = Quest(
            id = 1L,
            name = "test quest",
            user = testUser(),
            position = Position.BACKEND,
            tagList = mutableListOf(),
            difficulty = 1,
            thumbnail = "test.png",
            detail = "test quest detail",
            subQuestList = mutableListOf(SubQuest(name = "sub quest 1"), SubQuest(name = "sub quest 2"))
        )
        fun testTag() = Tag(1L, "tag1")
        fun testQuestParticipation() = QuestParticipation(
            QuestParticipationPK(testUser().id!!, testQuest().id!!),
            testQuest(),
            testUser()
        )
    }
}