package com.yapp.giljob.domain.position.repository

import com.yapp.giljob.domain.position.domain.Position
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PositionRepository: JpaRepository<Position, Long> {
}