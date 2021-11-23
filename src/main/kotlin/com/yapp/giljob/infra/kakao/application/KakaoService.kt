package com.yapp.giljob.infra.kakao.application

import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Service
class KakaoService {
    @Value("\${spring.social.kakao}")
    private lateinit var kakaoUrl: String

    fun getKakaoIdFromToken(kakaoAccessToken: String): String {
        val content = getResponseFromKakao(kakaoAccessToken)
        return getIdFromKakaoResponse(content)
    }

    fun getResponseFromKakao(kakaoAccessToken: String): String {
        try{
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
        } catch (e: Exception) {
            throw BusinessException(ErrorCode.CAN_NOT_GET_KAKAO_ID_ERROR)
        }
    }

    fun getIdFromKakaoResponse(content: String): String {
        val obj = JSONObject(content)
        return obj.optString("id")
    }
}