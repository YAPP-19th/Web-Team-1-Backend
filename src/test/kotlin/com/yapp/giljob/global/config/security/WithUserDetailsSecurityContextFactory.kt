package com.yapp.giljob.global.config.security

import com.yapp.giljob.global.common.domain.EntityFactory
import com.yapp.giljob.global.config.security.user.UserDetailsImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.test.context.support.WithSecurityContextFactory

internal class WithUserDetailsSecurityContextFactory : WithSecurityContextFactory<GiljobTestUser> {
    override fun createSecurityContext(annotation: GiljobTestUser?): SecurityContext {
        val principal: UserDetails = UserDetailsImpl(EntityFactory.testUser())
        val authentication: Authentication = UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        return context
    }
}