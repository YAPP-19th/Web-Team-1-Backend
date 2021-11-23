package com.yapp.giljob.domain.quest.dao

interface QuestParticipationSupportRepository {
    fun countParticipants(): Long
    fun countQuests(): Long
}