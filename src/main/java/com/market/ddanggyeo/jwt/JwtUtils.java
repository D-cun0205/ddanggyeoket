package com.market.ddanggyeo.jwt;

import com.market.ddanggyeo.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtils {
    private final UserDetailsServiceImpl userDetailsService;

    JwtUtils(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private static final Long EXPIRE_TIME = 60L * 60 * 6;
    private static final String JWT_SECRET = "ddanggyeo";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    //유효기간: 6시간
    public String createToken(String email) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SIGNATURE_ALGORITHM, JWT_SECRET)
                .compact();
    }

    //유효기간: 2주
    public String createRefreshToken(String email) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME * 4 * 14))
                .signWith(SIGNATURE_ALGORITHM, JWT_SECRET)
                .compact();
    }

    public Boolean validation(String token) {
        Claims claims = getAllClaims(token);
        Date exp = claims.getExpiration();
        return exp.after(new Date());
    }

    public String parseEmail(String token) {
        Claims claims = getAllClaims(token);
        return (String) claims.get("email");
    }

    public Authentication getAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
