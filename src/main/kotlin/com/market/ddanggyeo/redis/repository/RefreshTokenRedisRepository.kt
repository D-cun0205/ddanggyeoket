package com.market.ddanggyeo.redis.repository

import com.market.ddanggyeo.redis.hashEntity.TokenHash
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRedisRepository: CrudRepository<TokenHash, String> {}