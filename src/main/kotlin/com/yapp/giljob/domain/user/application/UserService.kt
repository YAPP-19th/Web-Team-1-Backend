package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.domain.user.dto.request.UserInfoUpdateRequestDto
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val abilityRepository: AbilityRepository,
) {
    @Transactional(readOnly = true)
    fun getAuthenticatedUserInfo(user: User) =
        UserInfoResponseDto(user.id!!, getUserAbility(user.id!!, user.position).point)

    private fun getUserAbility(userId: Long, position: Position) =
        abilityRepository.findByUserIdAndPosition(userId, position)
            ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)

    @Transactional
    fun updateUserInfo(userId: Long, requestDto: UserInfoUpdateRequestDto) {
        val user = userRepository.findByIdOrNull(userId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        user.nickname = requestDto.nickname
        user.position = requestDto.position
    }
}