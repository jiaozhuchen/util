package com.cc.libra.service;

import com.cc.libra.models.UserInfo;
import org.springframework.stereotype.Service;

/**
 * Created by chenchen39 on 2018/9/29.
 */
@Service
public class CacheService {

    public void putCacheUser(String ticketName, UserInfo userInfo) {
        UserInfo.putUser(ticketName, userInfo);
    }

    public UserInfo getCacheUser(String ticketName) {
        return UserInfo.getUser(ticketName);
    }
}
