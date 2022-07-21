package com.market.ddanggyeo.account.controller

import com.market.ddanggyeo.account.dto.AccountDto
import com.market.ddanggyeo.account.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/account")
class AccountController(private val accountService: AccountService) {

    @PostMapping("/signin")
    fun signin(@RequestBody accountDto: AccountDto) =
        ResponseEntity.ok().body(accountService.signin(accountDto))

    @PostMapping("/signup")
    fun signup(@RequestBody accountDto: AccountDto) =
        ResponseEntity.ok().body(accountService.signup(accountDto))

    @GetMapping("/refreshToken")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) =
        ResponseEntity.ok().body(accountService.refreshToken(request, response))

}