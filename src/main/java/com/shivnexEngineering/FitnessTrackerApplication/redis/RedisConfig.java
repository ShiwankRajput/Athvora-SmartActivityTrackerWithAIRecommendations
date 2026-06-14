package com.shivnexEngineering.FitnessTrackerApplication.redis;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

    private ObjectMapper objectMapper;    

    public RedisConfig(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    // Generally use by manual set and get method for setting and getting the keys in - Default Rediervice class
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        return redisTemplate;

    }

    // Generally use by the annotation - Cacheable, CacheEvict, CachePut
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        
        RedisCacheConfiguration config =
            RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(2))
                    .serializeKeysWith(
                            RedisSerializationContext
                                    .SerializationPair
                                    .fromSerializer(
                                            new StringRedisSerializer()
                                    )
                    )
                    .serializeValuesWith(
                            RedisSerializationContext
                                    .SerializationPair
                                    .fromSerializer(
                                            new GenericJackson2JsonRedisSerializer(objectMapper)
                                    )
                    );

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();

    }

}
