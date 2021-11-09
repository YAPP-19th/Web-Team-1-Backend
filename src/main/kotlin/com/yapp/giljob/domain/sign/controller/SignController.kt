package com.yapp.giljob.domain.sign.controller

import com.yapp.giljob.domain.sign.dto.request.SignInRequest
import com.yapp.giljob.domain.sign.dto.request.SignUpRequest
import com.yapp.giljob.domain.sign.service.SignService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class SignController (private val signService: SignService){

    @PostMapping("/sign-up")
    fun signUp(@Validated @RequestBody signUpRequest: SignUpRequest, response: HttpServletResponse) {
        val user = signService.signUp(signUpRequest, response)
        signService.setAccessTokenInHeader(response, user)
    }

    @PostMapping("/sign-in")
    fun signIn(@Validated @RequestBody signInRequest: SignInRequest, response: HttpServletResponse) {
        val user = signService.signIn(signInRequest, response)
        signService.setAccessTokenInHeader(response, user)
    }
}
