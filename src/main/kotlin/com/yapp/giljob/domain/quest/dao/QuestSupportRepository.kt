package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.vo.QuestSupportVo

interface QuestSupportRepository {
    fun findByIdLessThanAndOrderByIdDesc(id: Long?, position: Position, size: Long): List<QuestSupportVo>
}