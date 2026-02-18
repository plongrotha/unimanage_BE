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

    public String getValue(String key) {
        try (var jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            log.error("Failed to get value from Redis: " + e.getMessage());
            return null;
        }
    }

    public void deleteValue(String key) {
        try (var jedis = jedisPool.getResource()) {
            jedis.del(key);
        } catch (Exception e) {
            log.error("Failed to delete value from Redis: " + e.getMessage());
        }
    }

    public void expireValue(String key, int seconds) {
        try (var jedis = jedisPool.getResource()) {
            jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("Failed to set expiration for key in Redis: " + e.getMessage());
        }
    }

    public void persistValue(String key) {
        try (var jedis = jedisPool.getResource()) {
            jedis.persist(key);
        } catch (Exception e) {
            log.error("Failed to persist key in Redis: " + e.getMessage());
        }
    }
}
