package com.cc.libra.utils;


import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

/**
 * Created by chenchen39 on 2018/9/29.
 */
public class MD5Util {

    /**对字符串进行MD5编码*/
    public static String encodeByMD5(String originString){

        if (originString!=null) {
            try {
                return DigestUtils.md5DigestAsHex(originString.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
