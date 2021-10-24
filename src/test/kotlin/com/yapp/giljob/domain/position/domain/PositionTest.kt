package com.yapp.giljob.domain.position.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `id가 같으면 같은 객체이다`() {
        // given
        val positionId = 1L

        val position1 = Position(positionId, "spring")
        val position2 = Position(positionId, "go")

        // when

        // then
        assertEquals(position1, position2)
    }
}