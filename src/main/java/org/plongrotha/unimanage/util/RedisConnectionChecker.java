package org.plongrotha.unimanage.util;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionChecker {

    private static final Logger log = LoggerFactory.getLogger(RedisConnectionChecker.class);

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisConnectionChecker(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void checkConnection() {
        try {
            assert redisTemplate.getConnectionFactory() != null;
            String ping = redisTemplate.getConnectionFactory().getConnection().ping();
            log.info("========================================");
            log.info("✅ REDIS CONNECTION SUCCESSFUL!");
            log.info("📡 Response: {}", ping);
            log.info("========================================");
        } catch (Exception e) {
            log.error("========================================");
            log.error("❌ REDIS CONNECTION FAILED!");
            log.error("Error: {}", e.getMessage());
            log.error("========================================");
        }
    }
}