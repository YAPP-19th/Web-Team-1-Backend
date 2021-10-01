package com.yapp.giljob.common.exception

import com.yapp.giljob.common.dto.BaseResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log : Logger get() = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BusinessException::class)
    fun handleBaseException(exception: BusinessException): ResponseEntity<BaseResponse<Unit>> {
        log.error(exception.message)
        return ResponseEntity.status(exception.exceptionCode.status).body(BaseResponse.exception(exception.exceptionCode))
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleUnexpectedException(exception: RuntimeException): ResponseEntity<BaseResponse<Unit>> {
        log.error("RuntimeException")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.exception(ExceptionCode.INTERNAL_SERVER_ERROR))
    }

}