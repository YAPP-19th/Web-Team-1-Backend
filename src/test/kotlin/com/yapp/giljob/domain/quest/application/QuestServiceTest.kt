package com.yapp.giljob.domain.quest.application

import com.yapp.giljob.domain.quest.dao.QuestRepository
import com.yapp.giljob.domain.tag.dao.TagRepository
import com.yapp.giljob.domain.tag.domain.QuestTag
import com.yapp.giljob.domain.tag.domain.QuestTagPK
import com.yapp.giljob.domain.tag.domain.Tag
import com.yapp.giljob.global.common.domain.EntityFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class QuestServiceTest {

    private lateinit var questService: QuestService

    @MockK
    private lateinit var questRepository: QuestRepository

    @MockK
    private lateinit var tagRepository: TagRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        questService = QuestService(questRepository, tagRepository)
    }

    @Nested
    inner class QuestTag {
        private val questWithoutId = EntityFactory.testQuestWithoutId()
        private val questWithId = EntityFactory.testQuestWithId()

        private lateinit var tagList: MutableList<Tag>

        private val savedTag = EntityFactory.testSavedTag()
        private val savedTagName = savedTag.name

        private val newTag = EntityFactory.testNewTag()
        private val newTagName = newTag.name

        @Test
        fun `이미 저장된 태그 저장되는지 테스트`() {
            // given
            tagList = mutableListOf(Tag(name = savedTagName))
            questWithId.tagList = mutableListOf(QuestTag(QuestTagPK(questWithId.id, savedTag.id)))

            every { tagRepository.findByName(savedTagName) } returns savedTag
            every { questRepository.save(any()) } returns questWithId

            // when
            val savedQuest = questService.saveQuest(questWithoutId, tagList)

            // them
            assertEquals(savedTag.id, savedQuest.tagList[0].id.tagId)
            assertEquals(questWithId.id, savedQuest.tagList[0].id.questId)
        }

        @Test
        fun `새로 저장된 태그 저장되는지 테스트`() {
            // given
            tagList = mutableListOf(Tag(name = newTagName))
            questWithId.tagList = mutableListOf(QuestTag(QuestTagPK(questWithId.id, newTag.id)))

            every { tagRepository.findByName(newTagName) } returns null
            every { tagRepository.save(any()) } returns newTag
            every { questRepository.save(any()) } returns questWithId

            // when
            val savedQuest = questService.saveQuest(questWithoutId, tagList)

            // then
            assertEquals(newTag.id, savedQuest.tagList[0].id.tagId)
            assertEquals(questWithId.id, savedQuest.tagList[0].id.questId)
        }
    }
}