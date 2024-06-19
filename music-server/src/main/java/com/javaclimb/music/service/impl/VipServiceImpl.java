package com.javaclimb.music.service.impl;

import com.javaclimb.music.service.VipService;
import com.javaclimb.music.utils.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VipServiceImpl implements VipService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean isVip(int userId) {
        String validUntilKey = RedisPrefixUtils.generatorVipValidUntilKey(userId + "");
        String validUntilData = stringRedisTemplate.opsForValue().get(validUntilKey);
        return validUntilData != null;
    }
}
