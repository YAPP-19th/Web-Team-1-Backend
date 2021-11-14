package com.yapp.giljob.global.config.security

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithUserDetailsSecurityContextFactory::class)
annotation class GiljobTestUser