package com.yapp.giljob.common.exception

import org.springframework.http.HttpStatus

enum class ExceptionCode(
    val status: HttpStatus,
    val message: String
) {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
}