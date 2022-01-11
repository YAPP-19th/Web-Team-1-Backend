package com.yapp.giljob.domain.subquest.api

import com.yapp.giljob.domain.subquest.application.SubQuestParticipationService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["https://giljob.netlify.app/"])
@RequestMapping("/api/subquests")
@RestController
class SubQuestController(
    private val subQuestParticipationService: SubQuestParticipationService
) {
    @PostMapping("/{subQuestId}/complete")
    fun completeSubQuest(@PathVariable subQuestId: Long, @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        subQuestParticipationService.completeSubQuest(subQuestId, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "서브퀘스트 완료 성공입니다."))
    }

    @PatchMapping("/{subQuestId}/cancel")
    fun cancelSubQuest(@PathVariable subQuestId: Long, @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        subQuestParticipationService.cancelSubQuest(subQuestId, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "서브퀘스트 취소 성공입니다."))
    }
}