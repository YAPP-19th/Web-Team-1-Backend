package com.yapp.giljob.global.util.calculator

import com.yapp.giljob.domain.user.domain.achieve.PointAchieve
import com.yapp.giljob.domain.user.domain.achieve.QuestAchieve

class AchieveCalculator {
    companion object {
        fun calculatePointAchieve(point: Long) =
            when (point) {
                in PointAchieve.LEVEL_5 until PointAchieve.LEVEL_4 -> 5
                in PointAchieve.LEVEL_4 until PointAchieve.LEVEL_3 -> 4
                in PointAchieve.LEVEL_3 until PointAchieve.LEVEL_2 -> 3
                in PointAchieve.LEVEL_2 until PointAchieve.LEVEL_1 -> 2
                else -> 1
            }

        fun calculateQuestAchieve(questCount: Long) =
            when (questCount) {
                in QuestAchieve.LEVEL_5 until QuestAchieve.LEVEL_4 -> 5
                in QuestAchieve.LEVEL_4 until QuestAchieve.LEVEL_3 -> 4
                in QuestAchieve.LEVEL_3 until QuestAchieve.LEVEL_2 -> 3
                in QuestAchieve.LEVEL_2 until QuestAchieve.LEVEL_1 -> 2
                else -> 1
            }
    }
}