package com.market.ddanggyeo.account.service

import com.market.ddanggyeo.account.dto.AccountDto
import com.market.ddanggyeo.account.entity.Account
import com.market.ddanggyeo.account.repository.AccountRepository
import com.market.ddanggyeo.jwt.JwtUtils
import com.market.ddanggyeo.security.WebSecurityConfig
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class AccountService(private val accountRepository: AccountRepository,
                     private val passwordEncoder: PasswordEncoder,
                     private val authenticationManager: AuthenticationManager,
                     private val jwtUtils: JwtUtils) {

    private val log = org.slf4j.LoggerFactory.getLogger(WebSecurityConfig::class.java)

    fun signin(accountDto: AccountDto): String {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(accountDto.email, accountDto.password, null)
            )
        } catch (e: BadCredentialsException) {
            throw BadCredentialsException("로그인 실패")
        }

        val token = jwtUtils.createToken(accountDto.email)
        val refreshToken = jwtUtils.createRefreshToken(accountDto.email)
        return "{token:$token, refreshToken:$refreshToken}"
    }

    fun signup(accountDto: AccountDto): String {
        accountRepository.save(Account(accountDto.email, passwordEncoder.encode(accountDto.password)))
        return accountDto.email
    }

    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): String? {
        val header = request.getHeader("authorization")
        var refreshAccessToken: String? = null
        if (header != null && header.startsWith("Bearer ")) {
            try {
                val refreshToken = header.substring("Bearer ".length)
                val email = jwtUtils.parseEmail(refreshToken)
                //refreshToken, email 로 RDB or NOSQL(redis) 에 유효성 체크하여 동일한 값이 있으면 accessToken 재발급
                refreshAccessToken = jwtUtils.createToken(email)
            } catch (e: Exception) {
                response.setHeader("error", e.message)
                response.status
            }
        }
        return refreshAccessToken
    }
}