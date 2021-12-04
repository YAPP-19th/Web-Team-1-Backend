package com.yapp.giljob.domain.user.api

import com.yapp.giljob.domain.user.application.UserService
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
import com.yapp.giljob.global.common.annotation.CurrentUser
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    fun getAuthenticatedUserInfo(@CurrentUser user: User): ResponseEntity<BaseResponse<UserInfoResponseDto>> {
        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK, "인증된 유저 정보 조회 성공입니다.",
                userService.getAuthenticatedUserInfo(user)
            )
        )
    }
}