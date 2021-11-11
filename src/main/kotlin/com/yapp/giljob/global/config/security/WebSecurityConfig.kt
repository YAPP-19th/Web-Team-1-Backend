package com.yapp.giljob.global.config.security

import com.yapp.giljob.global.config.security.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@ComponentScan
class WebSecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/h2-console/**",
            "/sign-up",
            "/sign-in",
            "/don't-pass-filter"
        )
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
        http.cors().disable()
        http.csrf().disable()
        http.formLogin().disable()
        http.addFilterBefore(JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }
}

