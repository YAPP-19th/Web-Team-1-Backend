package com.yapp.giljob.domain.sign.dto.response

data class SignInResponseDto (
    val isSignedUp: Boolean,
    val accessToken: String?
)