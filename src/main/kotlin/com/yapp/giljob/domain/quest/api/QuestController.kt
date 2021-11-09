package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.quest.dto.QuestRequest
import com.yapp.giljob.domain.quest.mapper.QuestMapper
import com.yapp.giljob.domain.tag.mapper.TagMapper
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quest")
class QuestController(
    private val questService: QuestService,

    private val questMapper: QuestMapper,
    private val tagMapper: TagMapper
) {
    @PostMapping
    fun saveQuest(questRequest: QuestRequest, @CurrentUser user: User) {
        questService.saveQuest(
            questMapper.toEntity(questRequest, user),
            questRequest.tagList.map { tagMapper.toEntity(it) })
    }
}