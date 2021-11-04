package com.yapp.giljob.domain.sign.service

import com.yapp.giljob.domain.sign.converter.SignConverter
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.global.config.security.jwt.JwtProvider
import com.yapp.giljob.domain.sign.dto.request.SignInRequest
import com.yapp.giljob.domain.sign.dto.request.SignUpRequest
import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.util.KakaoUtil.Companion.getKakaoIdFromToken
import org.json.JSONObject
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.servlet.http.HttpServletResponse

@Service
class SignService (
    private val jwtProvider: JwtProvider,
    private val signRepository: SignRepository
    ) {

     fun signUp(signUpRequest: SignUpRequest, response: HttpServletResponse) {

         val kakaoId = getKakaoIdFromToken(signUpRequest.kakaoAccessToken)
         val converter = Mappers.getMapper(SignConverter::class.java)

         signRepository.findBySocialId(kakaoId)?.let { throw BusinessException(ErrorCode.ALREADY_SIGN_UP_USER_ERROR) }

         val user = converter.signUpConvertToModel(signUpRequest)
         user.socialId = kakaoId
         signRepository.save(user)

         returnWithAccessToken(response, user)
     }

     fun signIn(signInRequest: SignInRequest, response: HttpServletResponse) {
         val kakaoId = getKakaoIdFromToken(signInRequest.kakaoAccessToken)

         val user = signRepository.findBySocialId(kakaoId) ?: throw BusinessException(ErrorCode.NOT_SIGN_UP_USER_ERROR)

         returnWithAccessToken(response, user)
     }

    fun returnWithAccessToken(response: HttpServletResponse, user: User) {
        val accessToken: String = jwtProvider.createAccessToken(user.id)
        response.setHeader("Authorization", accessToken)
    }
}