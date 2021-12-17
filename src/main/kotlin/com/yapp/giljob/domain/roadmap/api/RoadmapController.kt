package com.yapp.giljob.domain.roadmap.api

import com.yapp.giljob.domain.roadmap.application.RoadmapService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roadmaps")
class RoadmapController(
    private val roadmapService: RoadmapService
) {
    @GetMapping("/{roadmapId}")
    fun getRoadmap(@PathVariable roadmapId: Long, @CurrentUser user: User) =
        ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "로드맵 조회 성공입니다.",
                roadmapService.getRoadmap(roadmapId, user.id!!)
            )
        )
}