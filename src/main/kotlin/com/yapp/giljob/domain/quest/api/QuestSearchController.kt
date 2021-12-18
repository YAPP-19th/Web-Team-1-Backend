package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestSearchService
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api/quests/search")
class QuestSearchController(
    private val questSearchService: QuestSearchService
) {
    @GetMapping
    fun searchQuestList(
        @RequestParam keyword: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "ALL") position: Position,
        @RequestParam(required = false, defaultValue = "16") size: Long
    ): ResponseEntity<BaseResponse<List<QuestResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(HttpStatus.OK, "퀘스트 검색 성공입니다.", questSearchService.search(keyword, position, size, cursor))
        )
    }

}