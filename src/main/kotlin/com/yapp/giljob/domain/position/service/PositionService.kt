package com.yapp.giljob.domain.position.service

import com.yapp.giljob.domain.position.repository.PositionRepository
import org.springframework.stereotype.Service

@Service
class PositionService(
    private val positionRepository: PositionRepository
) {
}