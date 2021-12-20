package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailInfoResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api/quests")
class QuestController(
    private val questService: QuestService
) {
    @PostMapping
    fun saveQuest(
        @RequestBody questSaveRequestDto: QuestSaveRequestDto,
        @CurrentUser user: User
    ): ResponseEntity<BaseResponse<Unit>> {
        questService.saveQuest(questSaveRequestDto, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "퀘스트 생성 성공입니다."))
    }

    @GetMapping
    fun getQuestList(
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "ALL") position: Position,
        @RequestParam(required = false, defaultValue = "16") size: Long
    ): ResponseEntity<BaseResponse<List<QuestResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(HttpStatus.OK, "퀘스트 리스트 조회 성공입니다.", questService.getQuestList(cursor, position, size))
        )
    }

    @GetMapping("/{questId}/info")
    fun getQuestDetailInfo(
        @PathVariable("questId") questId: Long
    ): ResponseEntity<BaseResponse<QuestDetailInfoResponseDto>> {
        return ResponseEntity.ok(
            BaseResponse.of(HttpStatus.OK, "퀘스트 상세 정보 조회 성공입니다.", questService.getQuestDetailInfo(questId))
        )
    }

    @GetMapping("/{questId}/user-quest-status")
    fun getUserQuestStatus(
        @PathVariable("questId") questId: Long,
        @CurrentUser user: User
    ): ResponseEntity<BaseResponse<String>>{
        return ResponseEntity.ok(
            BaseResponse.of(HttpStatus.OK, "유저의 퀘스트에 대한 정보 성공입니다.", questService.getUserQuestStatus(questId, user))
        )
    }
}