package com.yapp.giljob.domain.user.application

import com.yapp.giljob.domain.user.domain.achieve.PointAchieve
import com.yapp.giljob.domain.user.domain.achieve.QuestAchieve

class AchieveCalculator {
    companion object {
        fun calculatePointAchieve(point: Long) =
            when (point) {
                in PointAchieve.LEVEL_5..PointAchieve.LEVEL_4 -> 5
                in PointAchieve.LEVEL_4..PointAchieve.LEVEL_3 -> 4
                in PointAchieve.LEVEL_3..PointAchieve.LEVEL_2 -> 3
                in PointAchieve.LEVEL_2..PointAchieve.LEVEL_1 -> 2
                else -> 0
            }

        fun calculateQuestAchieve(questCount: Long) =
            when (questCount) {
                in QuestAchieve.LEVEL_5..QuestAchieve.LEVEL_4 -> 5
                in QuestAchieve.LEVEL_4..QuestAchieve.LEVEL_3 -> 4
                in QuestAchieve.LEVEL_3..QuestAchieve.LEVEL_2 -> 3
                in QuestAchieve.LEVEL_2..QuestAchieve.LEVEL_1 -> 2
                else -> 0
            }
    }
}