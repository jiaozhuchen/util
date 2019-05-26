package com.cc.libra.utils;

import com.cc.libra.models.MethodInfo;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchen39 on 2018/8/13.
 */
public class MethodUtil {

    public static List<MethodInfo> getAllMethodsByClass(Class clazz, Object bean) {
        List methodInfoList = new ArrayList();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if ((Modifier.isNative(modifiers)) || (Modifier.isAbstract(modifiers)))
                continue;
            if (Modifier.isInterface(modifiers)) {
                continue;
            }
            MethodInfo methodInfo = new MethodInfo();
            if ((bean == null) && (Modifier.isStatic(modifiers))) {
                methodInfo.setStatic(true);
            } else {
                if (bean == null)
                    continue;
                methodInfo.setStatic(false);
                methodInfo.setBean(bean);
            }

            String methodName = method.getName();
            Type[] parameterTypes = method.getGenericParameterTypes();
            List paramNameList = new ArrayList();
            for (Type sinParma : parameterTypes) {
                String[] itemArr = sinParma.toString().split("<");
                String[] arr = itemArr[0].split("\\.");
                paramNameList.add(arr[(arr.length - 1)]);
            }
            methodInfo.setMethodName(methodName + "(" + StringUtils.join(paramNameList, ",") + ")");
            methodInfo.setMethodId(clazz.getName() + "_" + methodName + "_" + StringUtils.join(paramNameList, "_"));
            methodInfo.setMethod(method);
            methodInfo.setBean(bean);
            methodInfo.setBeanName(clazz.getName());
            methodInfoList.add(methodInfo);
        }
        return methodInfoList;
    }

    public static List<MethodInfo> getAllMethods(Object bean) {
        return getAllMethodsByClass(bean.getClass(), bean);
    }

    public static String getBeanNameByMethodId(String methodId) {
        String[] itemArr = methodId.split("_");
        String[] arr = itemArr[0].split("\\.");
        String clazzName = arr[(arr.length - 1)];
        String beanName = clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1);
        return beanName;
    }

    public static List<MethodInfo> getAllStaticMethods(String className) {
        try {
            Class clazz = Class.forName(className);
            return getAllMethodsByClass(clazz, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
