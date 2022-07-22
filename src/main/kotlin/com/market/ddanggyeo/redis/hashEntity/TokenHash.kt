package com.market.ddanggyeo.redis.hashEntity

import org.springframework.data.redis.core.RedisHash
import javax.persistence.Id

@RedisHash("refreshToken")
data class TokenHash(
    @Id
    val id: String? = null,
    val email: String? = null,
    val refreshToken: String? = null
)
