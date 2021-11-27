package com.yapp.giljob.domain.subquest.dao

import com.yapp.giljob.domain.subquest.domain.SubQuestParticipated
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipatedPK
import org.springframework.data.jpa.repository.JpaRepository

interface SubQuestParticipationRepository: JpaRepository<SubQuestParticipated, SubQuestParticipatedPK>