package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.quest.application.QuestParticipationService
import com.yapp.giljob.domain.quest.dto.request.QuestReviewCreateRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestCountResponseDto
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
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

    @PatchMapping("/{questId}/complete")
    fun completeQuest(@PathVariable questId: Long, @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        questParticipationService.completeQuest(questId, user)
        return ResponseEntity.ok(
            BaseResponse.of(HttpStatus.OK, "퀘스트 완료 성공입니다.")
        )
    }

    @PatchMapping("/{questId}/review")
    fun createQuestReview(
        @PathVariable questId: Long,
        @RequestBody questReviewCreateRequestDto: QuestReviewCreateRequestDto,
        @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        questParticipationService.createQuestReview(questId, questReviewCreateRequestDto, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "퀘스트 리뷰 작성 성공입니다."))
    }

    @GetMapping("/{questId}/participation/status")
    fun getQuestParticipationStatus(
        @PathVariable("questId") questId: Long,
        @RequestParam(value = "userId") userId:Long
    ): ResponseEntity<BaseResponse<String>>{
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "유저의 퀘스트에 대한 정보 성공입니다.",
                questParticipationService.getQuestParticipationStatus(questId, userId))
        )
    }
}