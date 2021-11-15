package com.yapp.giljob.domain.sign.application

import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.domain.user.domain.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class SignRepositoryTest @Autowired constructor(
    val signRepository: SignRepository
){

    @Test
    fun `findBySocialId 성공`() {
        val user = signRepository.findBySocialId("unknown_social_id")
        assertNull(user)
    }

    @Test
    fun `save user 성공`() {

        val saveUser = User(
            socialId = "kakaoId",
            nickname = "닉네임",
            position = Position.BACKEND
        )

        val user = signRepository.save(saveUser)
        assertEquals(user.socialId, saveUser.socialId)
        assertEquals(user.nickname, saveUser.nickname)
        assertEquals(user.position, saveUser.position)
    }

    @Test
    fun `save user 후 findBySocialId 성공`() {
        val saveUser = User(
            socialId = "kakaoId",
            nickname = "닉네임",
            position = Position.BACKEND
        )

       signRepository.save(saveUser)

        val userFromRepository = signRepository.findBySocialId("kakaoId")
        assertEquals(userFromRepository!!.socialId, saveUser.socialId)
        assertEquals(userFromRepository!!.position, saveUser.position)
        assertEquals(userFromRepository!!.nickname, saveUser.nickname)
    }
}