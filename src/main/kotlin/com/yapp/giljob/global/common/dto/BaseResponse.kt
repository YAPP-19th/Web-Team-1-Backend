package com.yapp.giljob.global.common.dto

import org.springframework.http.HttpStatus

class BaseResponse<T>(
    val status: Int,
    val message: String?,
    val data: T?
) {
    private constructor(status: HttpStatus, message: String?) : this(status.value(), message, null)

    companion object {
        fun <T> of(status: HttpStatus, message: String?, data: T?): BaseResponse<T> {
            return BaseResponse(status.value(), message, data)
        }

        fun of(status: HttpStatus, message: String?): BaseResponse<Unit> {
            return BaseResponse(status, message)
        }
    }
}