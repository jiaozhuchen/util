package com.cc.libra.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.cc.libra.models.MethodInvokeRequest;
import com.cc.libra.utils.ToolsSpringContextUtil;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.alibaba.fastjson.JSON.DEFAULT_PARSER_FEATURE;
import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by chenchen39 on 2018/8/14.
 */
@Service
public class MethodInvokeService {

    public Object getMethodInvoke(MethodInvokeRequest methodInvokeRequest) {
        try {

            Object bean = getBeanByRequest(methodInvokeRequest);

            Method method = getMethodByRequest(methodInvokeRequest);

            String inputJson = methodInvokeRequest.getRequest();

            return invokeMethod(bean, method, inputJson);
        } catch (Exception e) {
            System.out.println("方法调用异常：" + e.getMessage());
            return "方法调用异常, " + e.getMessage();
        }
    }

    private Object getBeanByRequest(MethodInvokeRequest methodInvokeRequest) {
        if(methodInvokeRequest.getIsStatic()) {
            return null;
        }else {
            String beanId = methodInvokeRequest.getBeanId();
            Object bean = ToolsSpringContextUtil.getBean(beanId);
            if(bean == null) {
                try {
                   bean = Class.forName(beanId).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return bean;
        }
    }

    private Method getMethodByRequest(MethodInvokeRequest methodInvokeRequest) throws ClassNotFoundException {
        Method[] methods = null;
        if(methodInvokeRequest.getIsStatic()) {
            Class clazz = Class.forName(methodInvokeRequest.getBeanId());
            methods = clazz.getMethods();
        }else {
            String beanId = methodInvokeRequest.getBeanId();
            Object bean = ToolsSpringContextUtil.getBean(beanId);
            methods = bean.getClass().getMethods();
        }
        if(methods == null || methods.length == 0) {
            return null;
        }
        Method method = null;
        for (Method tempethod : methods) {
            if(tempethod.getName().equals(methodInvokeRequest.getMethod())) {
                method = tempethod;
                break;
            }
        }
        return method;
    }

    private Object invokeMethod(Object bean, Method method, String inputJson) throws InvocationTargetException, IllegalAccessException, ParseException {
        Object resultObj = null;
        Type[] ts = method.getGenericParameterTypes();
        if(Modifier.isStatic(method.getModifiers())) {
            bean = null;
        }
        if (ts.length == 1) {
            try {
                inputJson = inputJson.trim();
                String json = inputJson;
                Object obj = parseObject(json, ts[0], new Feature[] { Feature.AllowSingleQuotes });
                resultObj = method.invoke(bean, new Object[] { obj });
            } catch (Exception e) {
                inputJson = inputJson.trim();
                String json = inputJson;
                resultObj = method.invoke(bean, new Object[] { json });
            }
        } else if (ts.length > 1)
        {
            List list = (List) JSON.parseObject(inputJson, new TypeReference<List>() {}.getType(), new ParserConfig(), DEFAULT_PARSER_FEATURE, new Feature[] { Feature.AllowSingleQuotes });

            Object[] param = new Object[ts.length];

            for (int i = 0; i < ts.length; ++i) {
                if (ts[i] == String.class) {
                    String para = (String)list.get(i);
                    Object paraObj = para;
                    if (para.equals("true")) {
                        paraObj = Boolean.valueOf(true);
                    } else if (para.equals("false")) {
                        paraObj = Boolean.valueOf(false);
                    }
                    param[i] = paraObj;
                }
                else if (ts[i] == Date.class) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    param[i] = dateFormat.parse((String)list.get(i));
                } else {
                    try {
                        param[i] = parseObject((String)list.get(i), ts[i], new Feature[] { Feature.AllowSingleQuotes });
                    } catch (Exception e) {
                        param[i] = list.get(i);
                    }
                }
            }
            resultObj = method.invoke(bean, param);
        }
        else {
            resultObj = method.invoke(bean, new Object[0]);
        }

        SerializeConfig mapping = new SerializeConfig();
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
        String result = JSON.toJSONString(resultObj, mapping, new SerializerFeature[] { });
        System.out.println(JSON.toJSON(resultObj));
        return resultObj;
    }
}
