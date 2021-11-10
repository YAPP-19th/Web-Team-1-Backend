package com.yapp.giljob.domain.position.domain

import com.fasterxml.jackson.annotation.JsonProperty

enum class Position{
    @JsonProperty("backend")
    BACKEND,
    @JsonProperty("frontend")
    FRONTEND
}