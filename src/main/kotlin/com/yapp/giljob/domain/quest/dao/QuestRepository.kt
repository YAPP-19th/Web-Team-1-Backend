package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.domain.Quest
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository: JpaRepository<Quest, Long> , QuestSupportRepository