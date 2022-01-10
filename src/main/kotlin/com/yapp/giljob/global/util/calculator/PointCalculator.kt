package com.yapp.giljob.global.util.calculator

class PointCalculator {
    companion object {
        private const val POINT_UNIT = 5
        private const val DEFAULT_POINT = 10L
        fun calculatePoint(difficulty: Int) = DEFAULT_POINT + difficulty * POINT_UNIT
    }
}