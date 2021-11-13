package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.quest.dto.QuestRequest
import com.yapp.giljob.domain.quest.dto.QuestResponse
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quests")
class QuestController(
    private val questService: QuestService
) {
    @PostMapping
    fun saveQuest(
        @RequestBody questRequest: QuestRequest,
        @CurrentUser user: User
    ): ResponseEntity<BaseResponse<Unit>> {
        questService.saveQuest(questRequest, user)
        return ResponseEntity(BaseResponse.of(CREATED, "퀘스트 생성 성공입니다."), CREATED)
    }

    @GetMapping
    fun getQuestList(
        @RequestParam(required = false) cursor: Long?,
        @PageableDefault(size = 16) pageable: Pageable
    ): ResponseEntity<BaseResponse<List<QuestResponse>>> {
        return ResponseEntity.ok(
            BaseResponse.of(OK, "퀘스트 리스트 조회 성공입니다.", questService.getQuestList(cursor, pageable))
        )
    }
}