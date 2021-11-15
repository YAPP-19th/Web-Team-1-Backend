package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.dto.QuestSupportDto

interface QuestSupportRepository {
    fun findByIdLessThanAndOrderByIdDesc(id: Long?, size: Long): List<QuestSupportDto>
}