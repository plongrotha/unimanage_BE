package org.plongrotha.unimanage.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;

@Service
@Slf4j
public class JedisService {

    private final JedisPool jedisPool;

    public JedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setValue(String key, String value) {
        try (var jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        } catch (Exception e) {
            log.error("Failed to set value in Redis: " + e.getMessage());
        }
    }
}
