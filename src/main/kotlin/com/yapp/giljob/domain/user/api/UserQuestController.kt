package com.yapp.giljob.domain.user.api

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.response.QuestByParticipantResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.user.application.UserQuestService
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/{userId}/quests")
class UserQuestController(
    private val userQuestService: UserQuestService
) {
    @GetMapping
    fun getQuestListByUser(
        @PathVariable userId: Long,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "ALL") position: Position,
        @RequestParam(required = false, defaultValue = "6") size: Long
    ): ResponseEntity<BaseResponse<List<QuestResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK, "유저가 생성한 퀘스트 리스트 조회 성공입니다.",
                userQuestService.getQuestListByUser(userId, cursor, position, size)
            )
        )
    }

    @GetMapping("/participation")
    fun getQuestListByParticipant(
        @PathVariable userId: Long,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "ALL") position: Position,
        @RequestParam(required = false, defaultValue = "6") size: Long
    ): ResponseEntity<BaseResponse<List<QuestByParticipantResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK, "유저가 참여한 퀘스트 리스트 조회 성공입니다.",
                userQuestService.getQuestListByParticipant(userId, cursor, position, size)
            )
        )
    }
}