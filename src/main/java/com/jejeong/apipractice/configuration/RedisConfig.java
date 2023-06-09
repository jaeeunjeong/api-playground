package com.jejeong.apipractice.configuration;

import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisURI redisURI = RedisURI.create(redisProperties.getUrl());
    RedisConfiguration redisConfiguration = LettuceConnectionFactory.createRedisConfiguration(
        redisURI);
    LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfiguration);
    factory.afterPropertiesSet();

    return factory;
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    redisTemplate.setConnectionFactory(redisConnectionFactory());

    return redisTemplate;
  }
}
