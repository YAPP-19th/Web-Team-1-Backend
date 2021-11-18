package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.domain.QuestParticipated
import com.yapp.giljob.domain.quest.domain.QuestParticipatedPK
import org.springframework.data.jpa.repository.JpaRepository

interface QuestParticipatedRepository: JpaRepository<QuestParticipated, QuestParticipatedPK>