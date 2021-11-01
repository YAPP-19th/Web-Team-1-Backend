package com.yapp.giljob.domain.sign.dto.request

data class SignUpRequest (
    val kakaoAccessToken: String,
    val positionId: Long,
    val nickname: String
    )