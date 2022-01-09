package com.yapp.giljob.domain.quest.api

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestService
import com.yapp.giljob.domain.quest.dto.QuestConditionDto
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailInfoResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestDetailSubQuestResponseDto
import com.yapp.giljob.domain.quest.dto.response.QuestResponseDto
import com.yapp.giljob.domain.subquest.application.SubQuestParticipationService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("/api/quests")
class QuestController(
    private val questService: QuestService,
    private val subQuestParticipationService: SubQuestParticipationService
) {
    @PostMapping
    fun saveQuest(
        @Valid @RequestBody questSaveRequestDto: QuestSaveRequestDto,
        @CurrentUser user: User
    ): ResponseEntity<BaseResponse<Unit>> {
        questService.saveQuest(questSaveRequestDto, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "퀘스트 생성 성공입니다."))
    }

    @GetMapping
    fun getQuestList(
        @RequestParam(required = false, defaultValue = "ALL") position: Position,
        @RequestParam(required = false) keyword: String?,
        @PageableDefault(size = 16) pageable: Pageable
    ): ResponseEntity<BaseResponse<QuestResponseDto<QuestDetailResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "퀘스트 리스트 조회 성공입니다.",
                questService.getQuestList(QuestConditionDto(position = position, keyword = keyword), pageable)
            )
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

    @GetMapping("/{questId}/subquests")
    fun getQuestDetailSubQuestProgress(
        @PathVariable("questId") questId: Long,
        @CurrentUser user: User
    ): ResponseEntity<BaseResponse<QuestDetailSubQuestResponseDto>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "퀘스트 서브 퀘스트 진행 현황 조회 성공입니다.",
                subQuestParticipationService.getQuestDetailSubQuestProgress(questId, user)
            )
        )
    }
}