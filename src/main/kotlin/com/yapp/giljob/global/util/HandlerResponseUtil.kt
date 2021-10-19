package com.yapp.giljob.global.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.yapp.giljob.global.error.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import javax.servlet.http.HttpServletResponse

class HandlerResponseUtil {
    companion object {
        fun doResponse(
            response: HttpServletResponse,
            exception: ErrorResponse,
            status: HttpStatus
        ){
            val mapper = jacksonObjectMapper()
            val content: String = mapper.writeValueAsString(exception)
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"
            response.status = status.value()
            response.writer.write(content)
        }
    }
}