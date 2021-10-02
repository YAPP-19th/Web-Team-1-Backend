package com.yapp.giljob.global.exception

open class BusinessException(
    val exceptionCode: ExceptionCode
) : RuntimeException()