package com.market.ddanggyeo.account.controller;

import com.market.ddanggyeo.account.dto.AccountDto;
import com.market.ddanggyeo.account.service.AccountService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {
    private final AccountService accountService;

    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok().body(accountService.signin(accountDto));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok().body(accountService.signup(accountDto));
    }

    @GetMapping(value = "/refreshToken")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(accountService.refreshToken(request, response));
    }
}
