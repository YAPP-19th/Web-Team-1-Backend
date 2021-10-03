package com.yapp.giljob.global.error.exception

import com.yapp.giljob.global.error.ErrorCode

open class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException()