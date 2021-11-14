package com.yapp.giljob.domain.tag.application

import com.yapp.giljob.domain.tag.dao.TagRepository
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TagServiceTest {

    private lateinit var tagService: TagService

    @MockK
    private lateinit var tagRepository: TagRepository

    private val quest = EntityFactory.testQuest()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        tagService = TagService(tagRepository)
    }

    @Test
    fun `새로 저장된 태그 테스트`() {
        // given
        every { tagRepository.findByName(any()) } returns null
        every { tagRepository.save(any()) } returns EntityFactory.testTag()

        // when
        val tagList = tagService.convertToQuestTagList(quest, listOf(DtoFactory.testTagRequest()))

        // then
        assertEquals(EntityFactory.testTag(), tagList[0].tag)
        assertEquals(quest, tagList[0].quest)
    }

    @Test
    fun `이미 저장된 태그 테스트`() {
        // given
        every { tagRepository.findByName(any()) } returns EntityFactory.testTag()

        // when
        val tagList = tagService.convertToQuestTagList(quest, listOf(DtoFactory.testTagRequest()))

        // then
        assertEquals(EntityFactory.testTag(), tagList[0].tag)
        assertEquals(quest, tagList[0].quest)
    }
}