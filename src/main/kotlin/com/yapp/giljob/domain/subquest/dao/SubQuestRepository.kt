package com.yapp.giljob.domain.subquest.dao

import com.yapp.giljob.domain.subquest.domain.SubQuest
import org.springframework.data.jpa.repository.JpaRepository

interface SubQuestRepository: JpaRepository<SubQuest, Long> {
    fun findByQuestId(questId: Long): List<SubQuest>
}
