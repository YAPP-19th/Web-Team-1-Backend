package com.yapp.giljob.global.config.security.jwt

import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.ErrorResponse
import com.yapp.giljob.global.util.HandlerResponseUtil
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
    ): GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        try {

            val token: String = jwtProvider.getTokenFromHeader(request as HttpServletRequest, response as HttpServletResponse)
            jwtProvider.decodeToken(token)

        } catch (e: ExpiredJwtException) {
            jwtFailureTask(response as HttpServletResponse, ErrorCode.EXPIRED_TOKEN_ERROR)
            return
        } catch (e: NullPointerException) {
            jwtFailureTask(response as HttpServletResponse, ErrorCode.NO_TOKEN_ERROR)
            return
        } catch (e: Exception) {
            jwtFailureTask(response as HttpServletResponse, ErrorCode.INVALID_TOKEN_ERROR)
            return
        }

        chain!!.doFilter(request, response)
    }

    private fun jwtFailureTask(
        response: HttpServletResponse,
        e: ErrorCode
    ){
        HandlerResponseUtil.doResponse(response, ErrorResponse.error(e), e.status)
    }

//    private fun saveUser{
//
//    }
}