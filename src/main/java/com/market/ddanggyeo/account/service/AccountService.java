package com.market.ddanggyeo.account.service;

import com.market.ddanggyeo.account.dto.AccountDto;
import com.market.ddanggyeo.account.entity.Account;
import com.market.ddanggyeo.account.repository.AccountRepository;
import com.market.ddanggyeo.jwt.JwtUtils;
import com.market.ddanggyeo.redis.hashEntity.TokenHash;
import com.market.ddanggyeo.redis.repository.RefreshTokenRedisRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenRedisRepository redisRepository;

    AccountService(AccountRepository accountRepository,
                   PasswordEncoder passwordEncoder,
                   AuthenticationManager authenticationManager,
                   JwtUtils jwtUtils,
                   RefreshTokenRedisRepository redisRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.redisRepository = redisRepository;
    }

    public String signin(AccountDto accountDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountDto.getEmail(), accountDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("로그인 실패");
        }

        String token = jwtUtils.createToken(accountDto.getEmail());
        String refreshToken = jwtUtils.createRefreshToken(accountDto.getEmail());
        TokenHash tokenHash = new TokenHash();
        tokenHash.setEmail(accountDto.getEmail());
        tokenHash.setRefreshToken(refreshToken);
        redisRepository.save(tokenHash);

        String createJiraIssue = "지라 이슈 커밋용 코드 추가";
        return "{token:" + token + ", refreshToken:" + refreshToken;
    }

    public String signup(AccountDto accountDto) {
        Account account = new Account();
        account.setEmail(accountDto.getEmail());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return accountDto.getEmail();
    }

    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader("authorization");
        String refreshAccessToken = "";
        if (header != null && header.startsWith("Bearer ")) {
            try {
                String refreshToken = header.substring("Bearer ".length());
                String email = jwtUtils.parseEmail(refreshToken);
                refreshAccessToken = jwtUtils.createToken(email);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
            }
        }
        return refreshAccessToken;
    }
}
