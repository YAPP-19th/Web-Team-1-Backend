package com.yapp.giljob.domain.sign.application

import com.yapp.giljob.domain.sign.repository.SignRepository
import com.yapp.giljob.global.common.domain.EntityFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class SignRepositoryTest @Autowired constructor(
    val signRepository: SignRepository
){
    private val user = EntityFactory.testUser()

    @Test
    fun `findBySocialId 성공`() {
        val user = signRepository.findBySocialId("unknown_social_id")
        assertNull(user)
    }

    @Test
    fun `save user 성공`() {

        val userFromRepository = signRepository.save(user)
        assertEquals(userFromRepository.socialId, user.socialId)
        assertEquals(userFromRepository.nickname, user.nickname)
        assertEquals(userFromRepository.position, user.position)
    }

    @Test
    fun `save user 후 findBySocialId 성공`() {

       signRepository.save(user)

        val userFromRepository = signRepository.findBySocialId("testSocialId")
        assertEquals(userFromRepository!!.socialId, user.socialId)
        assertEquals(userFromRepository!!.position, user.position)
        assertEquals(userFromRepository!!.nickname, user.nickname)
    }
}