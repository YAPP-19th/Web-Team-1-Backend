package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.quest.application.QuestParticipationService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quests/{questId}")
class QuestParticipationController(
    private val questParticipationService: QuestParticipationService
) {
    @PostMapping("/participation")
    fun participateQuest(@PathVariable questId: Long, @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        questParticipationService.participateQuest(questId, user)
        return ResponseEntity(BaseResponse.of(HttpStatus.CREATED, "퀘스트 참여 성공입니다."), HttpStatus.CREATED)
    }
}