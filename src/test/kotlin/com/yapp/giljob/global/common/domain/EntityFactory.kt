package com.yapp.giljob.global.common.domain

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.domain.Quest
import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.quest.domain.QuestParticipationPK
import com.yapp.giljob.domain.roadmap.domain.Roadmap
import com.yapp.giljob.domain.roadmap.domain.RoadmapScrap
import com.yapp.giljob.domain.roadmap.domain.RoadmapScrapPK
import com.yapp.giljob.domain.subquest.domain.SubQuest
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipation
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipationPK
import com.yapp.giljob.domain.tag.domain.Tag
import com.yapp.giljob.domain.user.domain.Ability
import com.yapp.giljob.domain.user.domain.User

class EntityFactory {
    companion object {
        fun testUser() = User(
            id = 1L,
            socialId = "testSocialId",
            nickname = "testNickname",
            intro = "testIntro",
            position = Position.BACKEND
        )
        fun testRoadmap() = Roadmap(
            id = 1L,
            name = "test roadmap",
            user = testUser(),
            position = Position.BACKEND
        )
        fun testRoadmapScrap() = RoadmapScrap(
            id = RoadmapScrapPK(testUser().id!!, testRoadmap().id!!),
            user = testUser(),
            roadmap = testRoadmap()
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
            subQuestList = mutableListOf()
        )
        fun testTag() = Tag(1L, "tag1")
        fun testQuestParticipation() = QuestParticipation(
            QuestParticipationPK(testUser().id!!, testQuest().id!!),
            testQuest(),
            testUser()
        )
        fun testSubQuest() = SubQuest(
            id = 1L,
            quest = testQuest(),
            name = "sub quest name"
        )
        fun testSubQuestParticipation() = SubQuestParticipation(
            SubQuestParticipationPK(testUser().id!!, testSubQuest().id!!),
            subQuest = testSubQuest(),
            participant = testUser(),
            isCompleted = true,
            quest = testQuest()
        )
    }
}