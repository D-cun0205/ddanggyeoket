package com.market.ddanggyeo.user.service;

import com.market.ddanggyeo.user.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    public String insertUserInfo(UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "";
    }
}
