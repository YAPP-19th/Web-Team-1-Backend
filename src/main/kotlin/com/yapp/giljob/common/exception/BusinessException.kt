package com.yapp.giljob.common.exception

open class BusinessException(
    val exceptionCode: ExceptionCode
) : RuntimeException()