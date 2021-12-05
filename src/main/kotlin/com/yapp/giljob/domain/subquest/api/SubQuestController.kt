package com.yapp.giljob.domain.subquest.api

import com.yapp.giljob.domain.subquest.application.SubQuestParticipationService
import com.yapp.giljob.domain.subquest.application.SubQuestService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/subquests")
@RestController
class SubQuestController(
    private val subQuestParticipationService: SubQuestParticipationService
) {
    @PostMapping("/{subQuestId}")
    fun completeSubQuest(@PathVariable subQuestId: Long, @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        subQuestParticipationService.completeSubQuest(subQuestId, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "서브퀘스트 완료 성공입니다."))
    }
}