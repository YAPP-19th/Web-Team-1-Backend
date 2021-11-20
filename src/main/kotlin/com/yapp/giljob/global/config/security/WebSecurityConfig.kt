package com.yapp.giljob.global.config.security

import com.yapp.giljob.global.config.security.jwt.JwtAuthenticationFilter
import com.yapp.giljob.global.config.security.jwt.JwtResolver
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@ComponentScan
class WebSecurityConfig(
    private val jwtResolver: JwtResolver
) : WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/h2-console/**",
            "/sign-up",
            "/sign-in",
            "/api/quests/count",
            "/don't-pass-filter",
            "/docs/index.html"
        )
        web.ignoring().antMatchers(HttpMethod.GET, "/api/quests")
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
        http.cors().disable()
        http.csrf().disable()
        http.formLogin().disable()
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/quests").authenticated()
        http.addFilterBefore(JwtAuthenticationFilter(jwtResolver), UsernamePasswordAuthenticationFilter::class.java)
    }
}

