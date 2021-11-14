package com.yapp.giljob.global.config.security.user

import com.yapp.giljob.domain.user.dao.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        return UserDetailsImpl(
            userRepository.findByIdOrNull(username.toLong())
        )
    }
}