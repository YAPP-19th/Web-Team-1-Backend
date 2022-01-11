package com.yapp.giljob.domain.position.api

import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["https://giljob.netlify.app/"])
@RestController
@RequestMapping("/api")
class PositionController(
    private val questService: QuestService,
) {
    @GetMapping("/quests/positions/count")
    fun getQuestPositionCount() = ResponseEntity.ok(
        BaseResponse.of(
            HttpStatus.OK,
            "포지션 별 퀘스트 개수 조회 성공입니다.",
            questService.getQuestPositionCount()
        )
    )
}