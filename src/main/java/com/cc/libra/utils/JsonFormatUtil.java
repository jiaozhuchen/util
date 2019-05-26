package com.cc.libra.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created by chenchen39 on 2018/8/13.
 */
public class JsonFormatUtil {

    private static SerializeConfig mapping = new SerializeConfig();

    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";
    //TODO chenchen
    public static String getJsonFormatString(Object object)
    {
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
        try {
            return JSON.toJSONString(object, mapping, new SerializerFeature[] { SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat }); } catch (Exception e) {
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(object);
    }
}
