package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.application.QuestHelper
import com.yapp.giljob.domain.quest.dao.QuestParticipationRepository
import com.yapp.giljob.domain.user.dao.AbilityRepository
import com.yapp.giljob.domain.user.dao.UserRepository
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.domain.user.dto.request.UserInfoUpdateRequestDto
import com.yapp.giljob.domain.user.dto.response.AbilityResponseDto
import com.yapp.giljob.domain.user.dto.response.AchieveResponseDto
import com.yapp.giljob.domain.user.dto.response.UserInfoResponseDto
import com.yapp.giljob.domain.user.dto.response.UserProfileResponseDto
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.global.util.calculator.AchieveCalculator.Companion.calculatePointAchieve
import com.yapp.giljob.global.util.calculator.AchieveCalculator.Companion.calculateQuestAchieve
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val abilityRepository: AbilityRepository,
    private val questParticipantRepository: QuestParticipationRepository,

    private val userMapper: UserMapper
) {
    @Transactional(readOnly = true)
    fun getUserInfo(user: User): UserInfoResponseDto {
        val ability = getUserAbility(user.id!!, user.position)
        return userMapper.toDto(user, ability)
    }

    @Transactional(readOnly = true)
    fun getUserProfile(userId: Long): UserProfileResponseDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        val abilityList = getAbilityListByUserId(userId)
        val ability =
            abilityList.find { it.position == user.position } ?: AbilityResponseDto(user.position, 0)
        val completedQuestCount =
            QuestHelper.countQuestsByParticipantIdAndCompleted(questParticipantRepository, userId)

        val achieve =
            AchieveResponseDto(calculatePointAchieve(ability.point), calculateQuestAchieve(completedQuestCount))

        return UserProfileResponseDto(
            userInfo = userMapper.toDto(user, ability),
            abilityList = abilityList,
            achieve = achieve
        )
    }

    private fun getUserAbility(userId: Long, position: Position): AbilityResponseDto {
        return abilityRepository.findByUserIdAndPosition(userId, position)?.let { userMapper.toDto(it) }
            ?: AbilityResponseDto(position, 0)
    }

    private fun getAbilityListByUserId(userId: Long): List<AbilityResponseDto> {
        val abilityList = abilityRepository.findByUserId(userId)
        return abilityList.map {
            userMapper.toDto(it)
        }
    }

    @Transactional
    fun updateUserInfo(userId: Long, requestDto: UserInfoUpdateRequestDto) {
        val user = userRepository.findByIdOrNull(userId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        user.updateInfo(requestDto.nickname, requestDto.position)
    }

    @Transactional
    fun updateUserIntro(userId: Long, intro: String) {
        val user = userRepository.findByIdOrNull(userId) ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        user.updateIntro(intro)
    }
}