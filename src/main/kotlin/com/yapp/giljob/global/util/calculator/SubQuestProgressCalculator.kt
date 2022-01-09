package com.yapp.giljob.global.util.calculator

class SubQuestProgressCalculator {
    companion object {
        fun calculateProgress(
            totalSubQuestCount: Int,
            subQuestCompletedCount: Long
        ) =
            subQuestCompletedCount.toDouble().div(totalSubQuestCount).times(100).toInt()
    }
}