package com.market.ddanggyeo.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(private val jwtUtils: JwtUtils): OncePerRequestFilter() {

    //token 으로 권한을 체크하고 authentication 을 생성해서 security 인증 필터를 통과하도록 조치
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader: String? = request.getHeader("Authorization")?:
            return filterChain.doFilter(request, response)
        val token: String = authorizationHeader?.substring("Bearer ".length)?:
            return filterChain.doFilter(request, response)

        if (jwtUtils.validation(token)) {
            val email = jwtUtils.parseEmail(token)
            val authentication: Authentication = jwtUtils.getAuthentication(email)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

}