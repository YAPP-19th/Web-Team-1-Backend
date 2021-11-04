package com.yapp.giljob.global.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KakaoUtilTest {
    @Test
    fun `getIdFromKakaoResponse 성공`() {
        val content = "{\n" +
                "    \"id\": 1,\n" +
                "    \"connected_at\": \"2021-10-18T05:26:52Z\"\n" +
                "}"

        val id = KakaoUtil.getIdFromKakaoResponse(content)

        Assertions.assertEquals(id, "1")
    }
}