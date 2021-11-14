package com.yapp.giljob.domain.tag.dao

import com.yapp.giljob.domain.tag.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {
    fun findByName(name: String): Tag?
}