package com.yapp.giljob.domain.sign.service

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.sign.dto.request.SignInRequest
import com.yapp.giljob.domain.sign.dto.request.SignUpRequest
import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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
    lateinit var signRepository: SignRepository

    @Spy
    @InjectMocks
    lateinit var signService: SignService

    var user: User by Delegates.notNull()
    var signUpRequest: SignUpRequest by Delegates.notNull()
    var signInRequest: SignInRequest by Delegates.notNull()
    var response: HttpServletResponse by Delegates.notNull()

    @BeforeAll
    fun setUp() {
        user = User(
            socialId = "socialId",
            nickname = "닉네임",
            position = Position.BACKEND
        )

        signUpRequest = SignUpRequest(
            kakaoAccessToken = "test",
            position = Position.BACKEND.toString(),
            nickname = "닉네임"
        )

        signInRequest = SignInRequest(
            kakaoAccessToken = "test"
        )

        response = mock(HttpServletResponse::class.java)
    }

    @Test
    fun `회원가입 성공`() {
        given(signRepository.findBySocialId(anyString())).willReturn(null)

        val accessToken = signService.signUp(signUpRequest, response)

        assertNotNull(accessToken)
    }

    @Test
    fun `로그인 성공`() {
        given(signRepository.findBySocialId(anyString())).willReturn(user)

        val accessToken = signService.signIn(signInRequest, response)

        assertNotNull(accessToken)
    }

    @Test
    fun `기가입자가 회원가입시 에러`() {
        given(signRepository.findBySocialId(anyString())).willReturn(user)

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            signService.signUp(signUpRequest, response)
        }

        assertEquals(exception.errorCode, ErrorCode.ALREADY_SIGN_UP_USER_ERROR)
    }

    @Test
    fun `미가입자가 로그인시 에러`() {
        given(signRepository.findBySocialId(anyString())).willReturn(null)

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            signService.signIn(signInRequest, response)
        }

        assertEquals(exception.errorCode, ErrorCode.NOT_SIGN_UP_USER_ERROR)
    }
}