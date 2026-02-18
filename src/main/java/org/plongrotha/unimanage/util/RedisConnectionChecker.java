package org.plongrotha.unimanage.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisConnectionChecker {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisConnectionChecker(@Lazy RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void checkConnection() {
        try {
            var connectionFactory = redisTemplate.getConnectionFactory();
            if (connectionFactory != null) {
                String ping = connectionFactory.getConnection().ping();
                log.info("✅ REDIS CONNECTION SUCCESSFUL: {}", ping);
            }
        } catch (Exception e) {
            log.error("❌ REDIS CONNECTION FAILED! App will fallback to Database.");
            log.error("Reason: {}", e.getMessage());
        }
    }
}