package com.yapp.giljob.domain.user.application

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AchieveCalculatorTest {

    @Nested
    inner class Point {
        @Test
        fun `point가 0이면 5급이다`() {
            assertEquals(5, AchieveCalculator.calculatePointAchieve(0L))
        }

        @Test
        fun `point가 250미만이면 5급이다`() {
            assertEquals(5, AchieveCalculator.calculatePointAchieve(249L))
        }

        @Test
        fun `point가 250이면 4급이다`() {
            assertEquals(4, AchieveCalculator.calculatePointAchieve(250L))
        }

        @Test
        fun `point가 1000이면 1급이다`() {
            assertEquals(1, AchieveCalculator.calculatePointAchieve(1000L))
        }

        @Test
        fun `point가 1000초과이면 1급이다`() {
            assertEquals(1, AchieveCalculator.calculatePointAchieve(1500L))
        }
    }

    @Nested
    inner class Quest {
        @Test
        fun `퀘스트 개수가 0이면 5급이다`() {
            assertEquals(5, AchieveCalculator.calculateQuestAchieve(0L))
        }

        @Test
        fun `퀘스트 개수가 25미만이면 5급이다`() {
            assertEquals(5, AchieveCalculator.calculateQuestAchieve(24L))
        }

        @Test
        fun `퀘스트 개수가 50이면 3급이다`() {
            assertEquals(3, AchieveCalculator.calculateQuestAchieve(50L))
        }

        @Test
        fun `퀘스트 개수가 100이면 1급이다`() {
            assertEquals(1, AchieveCalculator.calculateQuestAchieve(100L))
        }

        @Test
        fun `퀘스트 개수가 100초과이면 1급이다`() {
            assertEquals(1, AchieveCalculator.calculateQuestAchieve(150L))
        }
    }
}