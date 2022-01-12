package com.yapp.giljob.domain.roadmap.api

import com.yapp.giljob.domain.roadmap.application.RoadmapService
import com.yapp.giljob.domain.roadmap.dto.request.RoadmapSaveRequestDto
import com.yapp.giljob.domain.roadmap.dto.response.RoadmapResponseDto
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api/roadmaps")
class RoadmapController(
    private val roadmapService: RoadmapService
) {
    @GetMapping("/{roadmapId}")
    fun getRoadmapDetail(@PathVariable roadmapId: Long, @CurrentUser user: User) =
        ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "로드맵 조회 성공입니다.",
                roadmapService.getRoadmapDetail(roadmapId, user)
            )
        )

    @DeleteMapping("/{roadmapId}")
    fun deleteRoadmap(@PathVariable roadmapId: Long, @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        roadmapService.deleteRoadmap(roadmapId, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "로드맵 삭제 성공입니다."))
    }

    @PostMapping
    fun saveRoadmap(
        @RequestBody roadmapSaveRequestDto: RoadmapSaveRequestDto,
        @CurrentUser user: User
    ): ResponseEntity<BaseResponse<Unit>> {
        roadmapService.saveRoadmap(roadmapSaveRequestDto, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "로드맵 등록 성공입니다."))
    }

    @GetMapping
    fun getRoadmapList(
        @PageableDefault(size = 4) pageable: Pageable
    ): ResponseEntity<BaseResponse<List<RoadmapResponseDto>>> {
        return ResponseEntity.ok(
            BaseResponse.of(HttpStatus.OK, "최근 등록된 로드맵 리스트 조회 성공입니다.", roadmapService.getRoadmapList(pageable))
        )
    }
}