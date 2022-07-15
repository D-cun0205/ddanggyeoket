package com.market.ddanggyeo.account.repository

import com.market.ddanggyeo.account.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long> {
    fun findByEmail(username: String): Account?


}