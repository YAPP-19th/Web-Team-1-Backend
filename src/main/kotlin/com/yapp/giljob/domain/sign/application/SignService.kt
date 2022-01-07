package com.yapp.giljob.domain.sign.application

import com.yapp.giljob.domain.sign.dto.request.SignInRequestDto
import com.yapp.giljob.domain.sign.dto.request.SignUpRequestDto
import com.yapp.giljob.domain.sign.dto.response.SignInResponseDto
import com.yapp.giljob.domain.sign.dto.response.SignUpResponseDto
import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.config.security.jwt.JwtUtil
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.infra.kakao.application.KakaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SignService{

    @Autowired
    private lateinit var signRepository: SignRepository

    @Autowired
    private lateinit var kakaoService: KakaoService

     fun signUp(signUpRequestDto: SignUpRequestDto): SignUpResponseDto {

         val kakaoId = kakaoService.getKakaoIdFromToken(signUpRequestDto.kakaoAccessToken)

         signRepository.findBySocialId(kakaoId)?.let { throw BusinessException(ErrorCode.ALREADY_SIGN_UP_USER_ERROR) }

         val user = User.of(signUpRequestDto, kakaoId)
         signRepository.save(user)

         return SignUpResponseDto(
             accessToken = JwtUtil.createAccessToken(user.id)
         )
     }

    fun signIn(signInRequestDto: SignInRequestDto): SignInResponseDto {
        val kakaoId = kakaoService.getKakaoIdFromToken(signInRequestDto.kakaoAccessToken)

        val user = signRepository.findBySocialId(kakaoId)
            ?: return SignInResponseDto(
                isSignedUp = false,
                accessToken = null
            )

        return SignInResponseDto(
            isSignedUp = true,
            accessToken = JwtUtil.createAccessToken(user.id)
        )
    }
}