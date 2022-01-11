package com.yapp.giljob.domain.user.api

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.QuestConditionDto
import com.yapp.giljob.domain.quest.dto.response.QuestByParticipantResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailResponseDto
import com.yapp.giljob.global.common.dto.ListResponseDto
import com.yapp.giljob.domain.user.application.UserQuestService
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["https://giljob.netlify.app/"])
@RestController
@RequestMapping("/api/users/{userId}/quests")
class UserQuestController(
    private val userQuestService: UserQuestService
) {
    @GetMapping
    fun getQuestListByUser(
        @PathVariable userId: Long,
        @RequestParam(required = false, defaultValue = "ALL") position: Position,
        @PageableDefault(size = 6) pageable: Pageable,
    ): ResponseEntity<BaseResponse<ListResponseDto<QuestDetailResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK, "유저가 생성한 퀘스트 리스트 조회 성공입니다.",
                userQuestService.getQuestListByUser(QuestConditionDto(position, userId), pageable)
            )
        )
    }

    @GetMapping("/participation")
    fun getQuestListByParticipant(
        @PathVariable userId: Long,
        @RequestParam(required = false, defaultValue = "false", value = "completed") isCompleted: Boolean,
        @PageableDefault(size = 6) pageable: Pageable
    ): ResponseEntity<BaseResponse<ListResponseDto<QuestByParticipantResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK, "유저가 참여한 퀘스트 리스트 조회 성공입니다.",
                userQuestService.getQuestListByParticipant(userId, isCompleted, pageable)
            )
        )
    }
}