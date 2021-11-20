package com.yapp.giljob.domain.sign.api

import com.yapp.giljob.domain.sign.dto.request.SignInRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignUpRequestDto
import com.yapp.giljob.domain.sign.application.SignService
import org.springframework.http.HttpHeaders
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class SignController (private val signService: SignService){

    @PostMapping("/sign-up")
    fun signUp(@Validated @RequestBody signUpRequestDto: SignUpRequestDto, response: HttpServletResponse) {
        val accessToken = signService.signUp(signUpRequestDto, response)
        response.setHeader(HttpHeaders.AUTHORIZATION , accessToken)
    }

    @PostMapping("/sign-in")
    fun signIn(@Validated @RequestBody signInRequestDto: SignInRequestDto, response: HttpServletResponse) {
        val accessToken = signService.signIn(signInRequestDto, response)
        response.setHeader(HttpHeaders.AUTHORIZATION , accessToken)
    }
}