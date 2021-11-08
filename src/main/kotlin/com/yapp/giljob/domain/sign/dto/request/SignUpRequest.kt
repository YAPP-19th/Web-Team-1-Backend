package com.yapp.giljob.domain.sign.dto.request

data class SignUpRequest (
    val kakaoAccessToken: String,
    val position: String,
    val nickname: String
    )