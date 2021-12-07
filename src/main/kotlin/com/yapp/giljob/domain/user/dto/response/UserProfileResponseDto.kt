package com.yapp.giljob.domain.user.dto.response

class UserProfileResponseDto(
    val userInfo: UserInfoResponseDto,
    val intro: String,
    val abilityList: List<AbilityResponseDto>
)