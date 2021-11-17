package com.yapp.giljob.domain.sign.service

import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.domain.sign.dto.request.SignInRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignUpRequestDto
import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.config.security.jwt.JwtUtil
import com.yapp.giljob.global.util.KakaoUtil.Companion.getKakaoIdFromToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class SignService{

    @Autowired
    private lateinit var signRepository: SignRepository

     fun signUp(signUpRequestDto: SignUpRequestDto, response: HttpServletResponse): String {

         val kakaoId = getKakaoIdFromToken(signUpRequestDto.kakaoAccessToken)

         signRepository.findBySocialId(kakaoId)?.let { throw BusinessException(ErrorCode.ALREADY_SIGN_UP_USER_ERROR) }

         val user = User.of(signUpRequestDto, kakaoId)
         signRepository.save(user)

         return JwtUtil.createAccessToken(user.id)
     }

    fun signIn(signInRequestDto: SignInRequestDto, response: HttpServletResponse): String {
        val kakaoId = getKakaoIdFromToken(signInRequestDto.kakaoAccessToken)

        val user = signRepository.findBySocialId(kakaoId) ?: throw BusinessException(ErrorCode.NOT_SIGN_UP_USER_ERROR)

        return JwtUtil.createAccessToken(user.id)
    }
}