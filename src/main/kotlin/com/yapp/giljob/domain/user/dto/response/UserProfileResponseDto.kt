package com.yapp.giljob.domain.user.dto.response

class UserProfileResponseDto(
    val userInfo: UserInfoResponseDto,
    val abilityList: List<AbilityResponseDto>,
    val achieve: AchieveResponseDto
)