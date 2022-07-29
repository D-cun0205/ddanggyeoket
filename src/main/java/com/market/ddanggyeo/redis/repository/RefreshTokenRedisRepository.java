package com.market.ddanggyeo.redis.repository;

import com.market.ddanggyeo.redis.hashEntity.TokenHash;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<TokenHash, String> { }
