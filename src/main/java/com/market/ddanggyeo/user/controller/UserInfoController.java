package com.market.ddanggyeo.user.controller;

import com.market.ddanggyeo.user.dto.UserDto;
import com.market.ddanggyeo.user.service.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserInfoController {

    private final UserInfoService userInfoService;

    UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public ResponseEntity<String> insertUserInfo(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userInfoService.insertUserInfo(userDto));
    }
}
