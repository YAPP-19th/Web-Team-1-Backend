package com.yapp.giljob.domain.sign.repository

import com.yapp.giljob.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface  SignRepository: JpaRepository<User, Long>{

    fun findBySocialId(socialId: String): User?

    fun deleteBySocialId(socialId: String)
}