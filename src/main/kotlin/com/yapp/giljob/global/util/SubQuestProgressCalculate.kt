package com.yapp.giljob.global.util

class SubQuestProgressCalculate {
    companion object{
        fun calculateProgress(
            totalSubQuestCount: Int,
            subQuestCompletedCount: Long) =
            subQuestCompletedCount.toDouble().div(totalSubQuestCount).times(100).toInt()
    }
}