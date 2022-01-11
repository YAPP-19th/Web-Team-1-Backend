package com.yapp.giljob.domain.sign.api

import com.yapp.giljob.domain.sign.application.SignService
import com.yapp.giljob.domain.sign.dto.request.SignInRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignUpRequestDto
import com.yapp.giljob.domain.sign.dto.response.SignInResponseDto
import com.yapp.giljob.domain.sign.dto.response.SignUpResponseDto
import com.yapp.giljob.global.common.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@CrossOrigin("https://giljob.netlify.app")
@RestController
class SignController (private val signService: SignService){

    @PostMapping("/api/sign-up")
    fun signUp(
        @Validated @RequestBody signUpRequestDto: SignUpRequestDto,
        response: HttpServletResponse
    ): ResponseEntity<BaseResponse<SignUpResponseDto>> {

        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "회원 가입 성공입니다.",
                signService.signUp(signUpRequestDto)
            )
        )

    }

    @PostMapping("/api/sign-in")
    fun signIn(
        @Validated @RequestBody signInRequestDto: SignInRequestDto,
        response: HttpServletResponse
    ): ResponseEntity<BaseResponse<SignInResponseDto>> {

        return ResponseEntity.ok(
            BaseResponse.of(
                HttpStatus.OK,
                "로그인 및 조회 성공입니다.",
                signService.signIn(signInRequestDto)
            )
        )

    }
}