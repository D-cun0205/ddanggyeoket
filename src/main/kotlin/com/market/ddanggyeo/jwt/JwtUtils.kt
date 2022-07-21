package com.market.ddanggyeo.jwt

import com.market.ddanggyeo.security.UserDetailsServiceImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils(private val userDetailsService: UserDetailsServiceImpl) {

    val EXPIRE_TIME: Long = 60L * 60 * 24
    val JWT_SECRET: String = "ddanggyeo"
    val SIGNATURE_ALG: SignatureAlgorithm = SignatureAlgorithm.HS256

    //토큰 유효기간 1일
    fun createToken(email: String): String {
        val claims: Claims =  Jwts.claims()
        claims["email"] = email
        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + EXPIRE_TIME))
            .signWith(SIGNATURE_ALG, JWT_SECRET)
            .compact()
    }

    //리프레쉬 토큰 유효기간 7일
    fun createRefreshToken(email: String): String {
        val claims: Claims = Jwts.claims()
        claims["email"] = email
        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + EXPIRE_TIME * 7))
            .signWith(SIGNATURE_ALG, JWT_SECRET)
            .compact()
    }

    fun validation(token: String): Boolean {
        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    fun parseEmail(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["email"] as String
    }

    fun getAuthentication(email: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(token)
            .body
    }
}