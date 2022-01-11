package com.yapp.giljob.domain.user.api

import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import com.yapp.giljob.domain.user.application.UserRoadmapService
import com.yapp.giljob.global.common.dto.BaseResponse
import com.yapp.giljob.global.common.dto.ListResponseDto
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("https://giljob.netlify.app")
@RestController
@RequestMapping("/api/users/{userId}/roadmaps")
class UserRoadmapController(
    private val userRoadmapService: UserRoadmapService
) {
    @GetMapping("/scrap")
    fun getScrapRoadmapList(
        @PathVariable userId: Long,
        @PageableDefault(size = 6) pageable: Pageable
    ) =
        ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK, "유저가 스크랩한 로드맵 리스트 조회 성공입니다.",
                userRoadmapService.getScrapRoadmapListByUser(userId, pageable)
            )
        )

    @GetMapping
    fun getRoadmapListByUser(
        @PathVariable userId: Long,
        @PageableDefault(size = 6) pageable: Pageable
    ): ResponseEntity<BaseResponse<ListResponseDto<RoadmapResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "유저가 등록한 로드맵 리스트 조회 성공입니다.",
                userRoadmapService.getRoadmapListByUser(userId, pageable)
            )
        )
    }
}