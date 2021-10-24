package com.yapp.giljob.domain.sign.service

import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.global.config.security.jwt.JwtProvider
import com.yapp.giljob.domain.sign.dto.request.SignInRequest
import com.yapp.giljob.domain.sign.dto.request.SignUpRequest
import org.json.JSONObject
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
    private val jwtProvider: JwtProvider
    ) {

    @Value("\${spring.social.kakao}")
    private lateinit var kakaoUrl: String

     fun signUp(signUpRequest: SignUpRequest, response: HttpServletResponse) {
         getKakaoIdFromToken(signUpRequest.kakaoAccessToken)

         // DB에서 확인 결과 기가입자일 경우 에러 던지기
         // DB에 사용자 저장
         // security context holder 에 사용자 추가

         returnWithAccessToken(response)
     }

     fun signIn(signInRequest: SignInRequest, response: HttpServletResponse) {
         getKakaoIdFromToken(signInRequest.kakaoAccessToken)

         // DB에서 확인 결과 미가입자일 경우 에러 던지기
         // security context holder 에 사용자 추가

        returnWithAccessToken(response)
     }

    fun getKakaoIdFromToken(kakaoAccessToken: String): Long {
        val content = getResponseFromKakao(kakaoAccessToken)
        return getIdFromKakaoResponse(content)
    }

    fun getResponseFromKakao(kakaoAccessToken: String): String {
        val url = URL(kakaoUrl)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection

        con.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer $kakaoAccessToken")

        val responseCode: Int = con.responseCode
        if (responseCode != HttpStatus.OK.value())
            throw BusinessException(ErrorCode.CAN_NOT_GET_KAKAO_ID_ERROR)

        val br = BufferedReader(InputStreamReader(con.inputStream))
        val content = br.readText()
        br.close()

        return content
    }

    fun getIdFromKakaoResponse(content: String): Long {
        val obj = JSONObject(content)
        return obj.optLong("id")
    }

    fun returnWithAccessToken(response: HttpServletResponse) {
        val accessToken: String = jwtProvider.createAccessToken("access token")
        response.setHeader("Authorization", accessToken)
    }
}