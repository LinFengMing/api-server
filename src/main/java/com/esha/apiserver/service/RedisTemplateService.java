package com.esha.apiserver.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisTemplateService {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    public void set(String key,Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
