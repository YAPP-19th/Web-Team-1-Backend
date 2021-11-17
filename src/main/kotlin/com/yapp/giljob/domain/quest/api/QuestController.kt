package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quests")
class QuestController(
    private val questService: QuestService
) {
    @PostMapping
    fun saveQuest(@RequestBody questSaveRequestDto: QuestSaveRequestDto, @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        questService.saveQuest(questSaveRequestDto, user)
        return ResponseEntity(BaseResponse.of(HttpStatus.CREATED, "퀘스트 생성 성공입니다."), HttpStatus.CREATED)
    }
}