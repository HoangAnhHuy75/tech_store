package com.techstore.techstore_backend.modules.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class DistributedLockService {
    private final RedisTemplate<String,String> redisTemplate;

    public boolean tryLock(String lockKey, String lockValue, long time) {
        long timeout = 4000;
        long retryInterval = 10;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeout) {
            boolean isLocked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofMinutes(time));
            if (isLocked) {
                return true;
            }
            try {
                Thread.sleep(retryInterval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    public void unLock(String lockKey, String lockValue) {
        String script = """
                if redis.call('GET', KEYS[1]) == ARGV[1] then
                  return redis.call('DEL', KEYS[1])
                else
                  return 0
                end
                """;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        redisTemplate.execute(redisScript, Collections.singletonList(lockKey),lockValue);
    }
}
