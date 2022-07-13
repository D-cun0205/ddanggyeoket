package com.market.ddanggyeo.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {

    private var secretKey = "ddanggyeoketsecretkey"

    private val tokenvalidTime = 30 * 60 * 1000L

    //의존성 주입 후 초기화 수행
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray());
    }

    fun createToken(userPk: String): String {
        val claims: Claims = Jwts.claims().setSubject(userPk)
        claims["userPk"] = userPk
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenvalidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUserPk(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }

    fun validateToken(jwtToken: String): Boolean {
        return try {
            val claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}