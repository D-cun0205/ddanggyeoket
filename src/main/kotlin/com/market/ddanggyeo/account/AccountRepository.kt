package com.market.ddanggyeo.account

import com.market.ddanggyeo.jwt.User
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Long, User> {

    fun findByEmail(username: String): User
}