package com.yapp.giljob.global.common.web

import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health-check")
class HealthCheckController {
    @GetMapping
    fun checkHealth() = ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "Health Check 성공입니다."))
}