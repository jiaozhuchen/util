package com.cc.libra.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchen39 on 2018/9/29.
 */
public class UserInfo {
    private String userName;
    private String passWord;

    private static Map<String , UserInfo> userInfoCachMap = new HashMap<String, UserInfo>();

    public static void putUser(String ticketName, UserInfo userInfo) {
        userInfoCachMap.put(ticketName, userInfo);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public static UserInfo getUser(String ticketName) {
        return userInfoCachMap.get(ticketName);
    }
}
