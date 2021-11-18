package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.vo.QuestSupportVo

interface QuestSupportRepository {
    fun findByIdLessThanAndOrderByIdDesc(id: Long?, size: Long): List<QuestSupportVo>
}