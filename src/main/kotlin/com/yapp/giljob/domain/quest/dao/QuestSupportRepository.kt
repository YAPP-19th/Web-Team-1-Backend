package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.vo.QuestSupportVo

interface QuestSupportRepository {
    fun findByIdLessThanAndOrderByIdDesc(questId: Long?, position: Position, userId: Long? = null, size: Long): List<QuestSupportVo>
}