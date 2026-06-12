package com.shivnexEngineering.FitnessTrackerApplication.redis;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // Generally use by manual set and get method for setting and getting the keys in - Default Rediervice class
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(Object.class));

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
                                            new JacksonJsonRedisSerializer<>(Object.class)
                                    )
                    );

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();

    }

}
