package com.yapp.giljob.domain.roadmap.api

import com.yapp.giljob.domain.roadmap.application.RoadmapScrapService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api/roadmaps")
class RoadmapScrapController(
    private val roadmapScrapService: RoadmapScrapService
) {
    @PostMapping("/{roadmapId}/scrap")
    fun scrap(@PathVariable roadmapId: Long, @CurrentUser user: User): ResponseEntity<BaseResponse<Unit>> {
        roadmapScrapService.scrap(roadmapId, user)
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "로드맵 스크랩 성공입니다."))
    }
}