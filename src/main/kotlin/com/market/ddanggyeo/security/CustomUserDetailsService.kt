package com.market.ddanggyeo.security

import com.market.ddanggyeo.account.AccountRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

class CustomUserDetailsService(private val accountRepository: AccountRepository,
                               private val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return accountRepository.findByEmail(username)?.let { User. }
    }
}