package com.shivnexEngineering.FitnessTrackerApplication.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, Object object, long timeoutInSeconds){
        redisTemplate.opsForValue()
            .set(key, object, timeoutInSeconds, TimeUnit.SECONDS);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

}
