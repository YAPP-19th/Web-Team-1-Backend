package com.yapp.giljob.global.infra.s3.api

import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.global.AbstractRestDocs
import com.yapp.giljob.global.common.dto.DtoFactory
import com.yapp.giljob.global.config.security.GiljobTestUser
import com.yapp.giljob.infra.s3.api.S3Controller
import com.yapp.giljob.infra.s3.application.S3Service
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.FileInputStream

@WebMvcTest(S3Controller::class)
class S3ControllerTest : AbstractRestDocs() {

    @MockBean
    private lateinit var s3Service: S3Service

    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    @GiljobTestUser
    fun upload() {
        val file = MockMultipartFile(
            "image",
            "test.png",
            "image/png",
            FileInputStream("/Users/we/Web-Team-1-Backend/src/main/resources/img/test.png")
        )

        given(s3Service.fileUplaod(any())).willReturn(DtoFactory.testS3UploadResponse())

        val result = mockMvc.perform(
            MockMvcRequestBuilders
                .multipart("/api/upload")
                .file("file", file.bytes)
                .header("Authorization", "Access Token")
        )

        result
            .andExpect(status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "upload/post",
                    HeaderDocumentation.requestHeaders(),
                    requestParameters(
                        parameterWithName("file").optional()
                            .description("업로드할 사진 파일")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("status")
                            .description("200"),
                        PayloadDocumentation.fieldWithPath("message")
                            .description("성공 메세지"),
                        PayloadDocumentation.fieldWithPath("data")
                            .description("업로드한 사진 url"),
                        PayloadDocumentation.fieldWithPath("data.fileUrl")
                            .description("업로드한 사진 url"),
                    )
                )
            )
    }
}