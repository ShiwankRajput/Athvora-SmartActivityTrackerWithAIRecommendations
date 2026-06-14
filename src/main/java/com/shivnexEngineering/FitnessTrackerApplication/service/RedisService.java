package com.shivnexEngineering.FitnessTrackerApplication.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final ObjectMapper objectMapper;
    private RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper){
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void save(String key, Object object, long timeoutInSeconds){
        redisTemplate.opsForValue()
            .set(key, object, timeoutInSeconds, TimeUnit.SECONDS);
    }

    // For Single Objects
    public <T> T get(String key, Class<T> clazz) {

        Object data = redisTemplate.opsForValue().get(key);

        if (data == null) {
            return null;
        }

        return objectMapper.convertValue(data, clazz);
    }

    // For Lists and Generic Types
    public <T> T get(
            String key,
            TypeReference<T> typeReference) {

        Object data = redisTemplate.opsForValue().get(key);

        if (data == null) {
            return null;
        }

        return objectMapper.convertValue(data, typeReference);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

}
