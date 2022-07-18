package com.market.ddanggyeo.user.service

import com.market.ddanggyeo.user.dto.UserDto
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserInfoService() {

    fun insertUserInfo(userDto: UserDto): String {
        val authentication = SecurityContextHolder.getContext().authentication

        return ""
    }
}