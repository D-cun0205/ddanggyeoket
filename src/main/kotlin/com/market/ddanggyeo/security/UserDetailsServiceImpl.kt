package com.market.ddanggyeo.security

import com.market.ddanggyeo.account.entity.Account
import com.market.ddanggyeo.account.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val accountRepository: AccountRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val account: Account = accountRepository.findByEmail(username)?:
            throw UsernameNotFoundException("존재하지 않은 계정입니다.")
        return UserDetailsImpl(account)
    }
}