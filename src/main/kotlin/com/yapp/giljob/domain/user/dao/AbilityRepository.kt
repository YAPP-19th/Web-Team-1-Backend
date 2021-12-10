package com.yapp.giljob.domain.user.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.user.domain.Ability
import org.springframework.data.jpa.repository.JpaRepository

interface AbilityRepository: JpaRepository<Ability, Long> {
    fun findByUserIdAndPosition(userId: Long, position: Position): Ability?
    fun findByUserId(userId: Long): List<Ability>
}