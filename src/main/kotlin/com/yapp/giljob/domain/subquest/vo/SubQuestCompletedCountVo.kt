package com.yapp.giljob.domain.subquest.vo

import com.querydsl.core.annotations.QueryProjection

class SubQuestCompletedCountVo @QueryProjection constructor(
    val questId: Long,
    val count: Long
)