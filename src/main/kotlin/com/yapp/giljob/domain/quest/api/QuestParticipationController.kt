package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.quest.application.QuestParticipationService
import com.yapp.giljob.domain.quest.dto.response.QuestCountResponseDto
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quests")
class QuestParticipationController(
    private val questParticipationService: QuestParticipationService
) {
    @PostMapping("/{questId}/participation")
    fun participateQuest(@PathVariable questId: Long, @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        questParticipationService.participateQuest(questId, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "퀘스트 참여 성공입니다."))
    }

    @GetMapping("/count")
    fun getAllQuestCount(): ResponseEntity<BaseResponse<QuestCountResponseDto>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "퀘스트 수 조회 성공입니다.",
                questParticipationService.getAllQuestCount()
            )
        )
    }
}