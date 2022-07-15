package com.market.ddanggyeo.account.controller

import com.market.ddanggyeo.account.dto.AccountDto
import com.market.ddanggyeo.account.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/account")
class AccountController(private val accountService: AccountService) {

    @PostMapping("/signin")
    fun signin(@RequestBody accountDto: AccountDto) =
        ResponseEntity.ok().body(accountService.signin(accountDto))

    @PostMapping("/signup")
    fun signup(@RequestBody accountDto: AccountDto) =
        ResponseEntity.ok().body(accountService.signup(accountDto))
}