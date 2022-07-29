package com.market.ddanggyeo.redis.hashEntity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@RedisHash("refreshToken")
public class TokenHash implements Serializable {
    @Id
    private String id;
    private String email;
    private String refreshToken;
}
