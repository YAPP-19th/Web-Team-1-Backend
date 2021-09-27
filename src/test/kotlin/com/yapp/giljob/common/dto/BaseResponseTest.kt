package com.yapp.giljob.common.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

private const val SUCCESS_DATA = "성공 데이터" // TODO 상수 선언 다시 확인해보기
private const val SUCCESS_MESSAGE = "성공 메세지"
private val OK = HttpStatus.OK

class BaseResponseTest {

    @Test
    fun `응답 데이터 포함 O`() {
        val response = BaseResponse.of(OK, SUCCESS_MESSAGE, SUCCESS_DATA)
        assertEquals(200, response.status)
        assertEquals(SUCCESS_MESSAGE, response.message)
        assertEquals(SUCCESS_DATA, response.data)
    }

    @Test
    fun `응답 데이터 포함 X`() {
        val response = BaseResponse.of(OK, SUCCESS_MESSAGE)
        assertEquals(200, response.status)
        assertEquals(SUCCESS_MESSAGE, response.message)
        assertNull(response.data)
    }

}