package com.yapp.giljob.domain.user.dao

import com.yapp.giljob.domain.user.domain.Ability
import org.springframework.data.jpa.repository.JpaRepository

interface AbilityRepository: JpaRepository<Ability, Long>