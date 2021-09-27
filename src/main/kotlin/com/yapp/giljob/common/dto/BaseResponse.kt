package com.yapp.giljob.common.dto

import org.springframework.http.HttpStatus

class BaseResponse<T>(
    val status: Int,
    val message: String?,
    val data: T?
) {
    companion object {
        fun <T> of(status: HttpStatus, message: String?, data: T?): BaseResponse<T> {
            return BaseResponse(status.value(), message, data)
        }

        fun of(status: HttpStatus, message: String?): BaseResponse<Unit> {
            return BaseResponse(status.value(), message, null)
        }
    }
}