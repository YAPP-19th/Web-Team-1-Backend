package com.yapp.giljob.domain.user.dao

import com.yapp.giljob.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>