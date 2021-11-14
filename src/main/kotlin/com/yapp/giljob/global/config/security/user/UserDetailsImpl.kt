package com.yapp.giljob.global.config.security.user

import com.yapp.giljob.domain.user.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority

import java.util.ArrayList

class UserDetailsImpl(
    val user: User?
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority(user?.authority))
        return authorities
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return user?.id.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}