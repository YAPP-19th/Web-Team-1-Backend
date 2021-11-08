package com.yapp.giljob.test

import com.yapp.giljob.global.AbstractRestDocs
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TestController::class)
class TestControllerTest : AbstractRestDocs() {
    @Test
    fun getTest() {
        val result = mockMvc.perform(get("/api/test"))
        result
            .andExpect(status().isOk)
            .andDo(
                document(
                    "test",
                    HeaderDocumentation.responseHeaders(),
                    PayloadDocumentation.responseFields(
                        fieldWithPath("status")
                            .description("상태 코드"),
                        fieldWithPath("message")
                            .description("성공 메세지"),
                        fieldWithPath("data")
                            .description("데이터")
                    )
                )
            )
    }
}
