package com.yapp.giljob.domain.quest.dao

import com.yapp.giljob.domain.quest.domain.QuestParticipation
import com.yapp.giljob.domain.quest.domain.QuestParticipationPK
import org.springframework.data.jpa.repository.JpaRepository

interface QuestParticipationRepository: JpaRepository<QuestParticipation, QuestParticipationPK>