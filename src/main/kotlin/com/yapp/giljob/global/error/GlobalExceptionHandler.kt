package com.yapp.giljob.global.error

import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.global.util.Log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBaseException(e: BusinessException): ResponseEntity<ErrorResponse> {
        Log.error(e.errorCode.message)
        return ResponseEntity.status(e.errorCode.status).body(ErrorResponse.error(e.errorCode))
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleUnexpectedException(e: RuntimeException): ResponseEntity<ErrorResponse> {
        Log.error(e.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.error(ErrorCode.INTERNAL_SERVER_ERROR))
    }

}