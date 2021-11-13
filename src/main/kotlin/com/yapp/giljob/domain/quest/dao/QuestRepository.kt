package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.domain.Quest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository: JpaRepository<Quest, Long> {
    fun findByIdLessThanOrderByIdDesc(id: Long, pageable: Pageable): List<Quest>
    fun findByOrderByIdDesc(pageable: Pageable): List<Quest>
}