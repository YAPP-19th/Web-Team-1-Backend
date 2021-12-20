package com.yapp.giljob.domain.subquest.vo

import com.yapp.giljob.domain.subquest.domain.SubQuest
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipation
import com.yapp.giljob.domain.subquest.domain.SubQuestParticipationPK

class SubQuestParticipationVo(
    val subQuest: SubQuest,
    val subQuestParticipationPK: SubQuestParticipationPK,
    val subQuestParticipation: SubQuestParticipation?
)