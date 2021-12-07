package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.domain.user.dto.response.AbilityResponseDto
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
import com.yapp.giljob.domain.user.dto.response.UserProfileResponseDto
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val abilityRepository: AbilityRepository,

    private val userMapper: UserMapper
) {
    @Transactional(readOnly = true)
    fun getUserInfo(user: User): UserInfoResponseDto {
        val ability = getUserAbility(user.id!!, user.position)
        return UserInfoResponseDto(userId = user.id!!, nickname = user.nickname, position = user.position, point = ability.point)
    }

    @Transactional(readOnly = true)
    fun getUserProfile(userId: Long): UserProfileResponseDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        return UserProfileResponseDto(
            userInfo = getUserInfo(user),
            intro = user.intro,
            abilityList = getAbilityListByUserId(userId)
        )
    }

    private fun getUserAbility(userId: Long, position: Position) =
        abilityRepository.findByUserIdAndPosition(userId, position)
            ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)

    private fun getAbilityListByUserId(userId: Long): List<AbilityResponseDto> {
        val abilityList = abilityRepository.findByUserId(userId)
        return abilityList.map {
            userMapper.toDto(it)
        }
    }
}