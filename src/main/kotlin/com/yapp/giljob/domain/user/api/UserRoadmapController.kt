package com.yapp.giljob.domain.user.api

import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import com.yapp.giljob.domain.user.application.UserRoadmapService
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api/users/{userId}/roadmaps")
class UserRoadmapController(
    private val userRoadmapService: UserRoadmapService
) {
    @GetMapping("/scrap")
    fun getScrapRoadmapList(
        @PathVariable userId: Long,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "6") size: Long,
    ) =
        ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK, "유저가 스크랩한 로드맵 리스트 조회 성공입니다.",
                userRoadmapService.getScrapRoadmapListByUser(userId, cursor, size)
            )
        )

    @GetMapping
    fun getRoadmapListByUser(
        @PathVariable userId: Long,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "6") size: Long
    ): ResponseEntity<BaseResponse<List<RoadmapResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "유저가 등록한 로드맵 리스트 조회 성공입니다.",
                userRoadmapService.getRoadmapListByUser(userId, cursor, size)
            )
        )
    }
}