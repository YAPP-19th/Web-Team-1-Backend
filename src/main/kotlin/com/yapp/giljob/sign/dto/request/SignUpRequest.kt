package com.yapp.giljob.sign.dto.request

data class SignUpRequest (
    val kakaoAccessToken: String,
    val positionId: Long,
    val nickname: String
    )