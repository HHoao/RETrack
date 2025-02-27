package com.rare_earth_track.portal.service.impl;

import com.rare_earth_track.common.service.RedisService;
import com.rare_earth_track.portal.bean.RetUserDetails;
import com.rare_earth_track.portal.service.RetUserCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * token缓存
 * @author hhoa
 * @date 2022/5/14
 **/
@Service
@RequiredArgsConstructor
public class RetUserCacheServiceImpl implements RetUserCacheService {
    @Value("${ret.redis.database}")
    private String redisDatabase;
    @Value("${ret.redis.expire.token}")
    private Long redisExpire;
    @Value("${ret.redis.key.user}")
    private String redisKeyRole;
    private final RedisService redisService;
    private String getUserNameKey(String username){
        return redisDatabase + ":" + redisKeyRole + ":" + username;
    }
    @Override
    public void expire(String username) {
        redisService.expire(getUserNameKey(username), redisExpire);
    }

    @Override
    public void expire(String username, Long expiration) {
        redisService.expire(getUserNameKey(username), expiration);
    }

    @Override
    public void setKey(String username, RetUserDetails userDetails) {
        redisService.set(getUserNameKey(username), userDetails, redisExpire);
    }

    @Override
    public boolean hasKey(String username){
        return redisService.hasKey(getUserNameKey(username));
    }

    @Override
    public RetUserDetails getKey(String username) {
        return (RetUserDetails) redisService.get(getUserNameKey(username));
    }

    @Override
    public void delKey(String username){
        redisService.del(getUserNameKey(username));
    }
}
