package com.yapp.giljob.domain.sign.application

import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.infra.kakao.application.KakaoService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.servlet.http.HttpServletResponse
import kotlin.properties.Delegates

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class SignServiceTest {

    @Mock
    lateinit var kakaoService: KakaoService

    @Mock
    lateinit var signRepository: SignRepository

    @Spy
    @InjectMocks
    lateinit var signService: SignService

    private val user = EntityFactory.testUser()
    private val signUpRequest = DtoFactory.testSignUpRequest()
    private val signInRequest = DtoFactory.testSignInRequest()

    var response: HttpServletResponse by Delegates.notNull()

    @BeforeAll
    fun setUp() {
        response = mock(HttpServletResponse::class.java)
    }

    @Test
    fun `회원가입 서비스 성공`() {
        given(signRepository.findBySocialId(anyString())).willReturn(null)
        given(kakaoService.getKakaoIdFromToken(anyString())).willReturn(user.socialId)

        val signUpResponseDto = signService.signUp(signUpRequest)

        assertNotNull(signUpResponseDto.accessToken)
    }

    @Test
    fun `로그인 서비스 성공`() {
        given(signRepository.findBySocialId(anyString())).willReturn(user)
        given(kakaoService.getKakaoIdFromToken(anyString())).willReturn(user.socialId)

        val signInResponseDto = signService.signIn(signInRequest)

        assertNotNull(signInResponseDto.accessToken)
        assertEquals(true, signInResponseDto.isSignedUp)
    }

    @Test
    fun `기가입자가 회원가입시 서비스에서 에러`() {
        given(signRepository.findBySocialId(anyString())).willReturn(user)
        given(kakaoService.getKakaoIdFromToken(anyString())).willReturn(user.socialId)

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            signService.signUp(signUpRequest)
        }

        assertEquals(exception.errorCode, ErrorCode.ALREADY_SIGN_UP_USER_ERROR)
    }

    @Test
    fun `미가입자가 로그인시 isSignedUp이 false로 리턴`() {
        given(signRepository.findBySocialId(anyString())).willReturn(null)
        given(kakaoService.getKakaoIdFromToken(anyString())).willReturn(user.socialId)

        val signInResponseDto = signService.signIn(signInRequest)

        assertEquals(false, signInResponseDto.isSignedUp)
        assertNull(signInResponseDto.accessToken)
    }
}