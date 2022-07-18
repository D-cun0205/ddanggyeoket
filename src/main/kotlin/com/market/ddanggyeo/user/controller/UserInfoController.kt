package com.market.ddanggyeo.user.controller

import com.market.ddanggyeo.account.dto.AccountDto
import com.market.ddanggyeo.user.dto.UserDto
import com.market.ddanggyeo.user.service.UserInfoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserInfoController(private val userInfoService: UserInfoService) {

    @RequestMapping("/insert")
    fun insertUserInfo(@RequestBody userDto: UserDto) =
        ResponseEntity.ok().body(userInfoService.insertUserInfo(userDto))

}