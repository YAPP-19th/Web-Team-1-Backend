package com.yapp.giljob.common.exception

import com.yapp.giljob.common.dto.BaseResponse
import com.yapp.giljob.common.util.Log
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBaseException(e: BusinessException): ResponseEntity<BaseResponse<Unit>> {
        Log.error(e.exceptionCode.message)
        return ResponseEntity.status(e.exceptionCode.status).body(BaseResponse.exception(e.exceptionCode))
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleUnexpectedException(e: RuntimeException): ResponseEntity<BaseResponse<Unit>> {
        Log.error(e.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.exception(ExceptionCode.INTERNAL_SERVER_ERROR))
    }

}