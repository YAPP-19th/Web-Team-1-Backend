package com.yapp.giljob.domain.sign.dto.request

data class SignUpRequestDto (
    val kakaoAccessToken: String,
    val position: String,
    val nickname: String,
    val profile: String
    )