package com.cc.libra.actions;

import com.cc.libra.models.MethodInfo;
import com.cc.libra.utils.MethodUtil;
import com.cc.libra.utils.ToolsSpringContextUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by chenchen39 on 2018/8/13.
 */
public abstract class Action {


    private static Map<String, Action> actionMap = new HashMap();

    private static Map<String, MethodInfo> methodMap = new HashMap();

    public static void putMethodInfo(List<MethodInfo> methodInfoList)
    {
        if ((methodInfoList == null) || (methodInfoList.size() == 0)) {
            return;
        }
        for (MethodInfo methodInfo : methodInfoList) {
            String methodId = methodInfo.getMethodId();
            if (methodMap.containsKey(methodId)) {
                continue;
            }
            methodMap.put(methodId, methodInfo);
        }
    }
    public MethodInfo getMethodInfo(String methodId, String beanName)
    {
        if (!(methodMap.containsKey(methodId)))
        {
            getAllMethodInfoList(ToolsSpringContextUtil.getBean(beanName));
            if (!(methodMap.containsKey(methodId))) {
                return null;
            }
            return ((MethodInfo)methodMap.get(methodId));
        }
        return ((MethodInfo)methodMap.get(methodId));
    }


    public static Action getAction(String actionName) {
        if(StringUtils.isBlank(actionName)) {
            return null;
        }
        actionName += "Action";
        if(actionMap.containsKey(actionName)) {
            return actionMap.get(actionName);
        }
        synchronized (Action.class) {
            if(actionMap.containsKey(actionName)) {
                return actionMap.get(actionName);
            }
            Object actionObj = ToolsSpringContextUtil.getBean(actionName);
            if(actionObj instanceof Action) {
                actionMap.put(actionName, (Action) actionObj);
            }
        }
        return actionMap.get(actionName);
    }

    public List<MethodInfo> getAllMethodInfoList(Object bean)
    {
        List allMethods = MethodUtil.getAllMethods(bean);
        putMethodInfo(allMethods);
        Collections.sort(allMethods, new Comparator()
        {
            public int compare(MethodInfo o1, MethodInfo o2) {
                return o1.getMethodName().compareToIgnoreCase(o2.getMethodName());
            }

            public int compare(Object o1, Object o2) {
                return ((MethodInfo)o1).getMethodName().compareTo(((MethodInfo)o2).getMethodName());
            }
        });
        return allMethods;
    }

    public List<MethodInfo> getAllStaticMethodInfoList(String className)
    {
        List allMethods = MethodUtil.getAllStaticMethods(className);
        putMethodInfo(allMethods);
        Collections.sort(allMethods, new Comparator()
        {
            public int compare(MethodInfo o1, MethodInfo o2) {
                return o1.getMethodName().compareToIgnoreCase(o2.getMethodName());
            }

            public int compare(Object o1, Object o2) {
                return ((MethodInfo)o1).getMethodName().compareTo(((MethodInfo)o2).getMethodName());
            }
        });
        return allMethods;
    }

    protected Object createSimpleObject(Type type)
            throws Exception
    {
        Class clazz = (Class)type;
        if (clazz.isArray()) {
            return Array.newInstance(clazz, 2);
        }
        String canonicalName = clazz.getCanonicalName();
        if ((canonicalName.equals("int")) || (canonicalName.equals("java.lang.Integer")) || (clazz.isEnum()))
        {
            return Integer.valueOf(0); }
        if (canonicalName.equals("java.lang.String"))
            return "";
        if ((canonicalName.equals("boolean")) || (canonicalName.equals("java.lang.Boolean")))
        {
            return Boolean.FALSE; }
        if ((canonicalName.equals("long")) || (canonicalName.equals("java.lang.Long")))
        {
            return Long.valueOf(0L); }
        if (canonicalName.equals("java.util.List"))
            return new ArrayList();
        if (canonicalName.equals("java.util.Map")) {
            return new HashMap();
        }

        return clazz.newInstance();
    }

    public Object createInstance(Type type)
            throws Exception
    {
        Object obj = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType)type;
            Type rawType = pType.getRawType();
            obj = createSimpleObject(rawType);
            Type[] ts = pType.getActualTypeArguments();
            if (ts.length == 1) {
                Type trueType = ts[0];
                Object obj1 = createInstance(trueType);
                ((List)obj).add(obj1);
            } else if (ts.length == 2) {
                Type keyType = ts[0];
                Type valType = ts[1];
                Object key = createInstance(keyType);
                Object val = createInstance(valType);
                ((Map)obj).put(key, val);
            }
        } else {
            obj = createSimpleObject(type);
        }
        return obj;
    }


    public abstract  void handle(HttpServletRequest request, HttpServletResponse response)
            throws Exception;

}
