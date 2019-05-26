package com.cc.libra.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;


/**
 * Created by chenchen39 on 2018/8/13.
 * 参数检查
 */
public final class CheckParamUtil {
    /**
     * 
     * @title isExistNull
     * @description 判断是否存在null
     * @author changpeng
     * @date 2014-11-26 下午9:08:02
     * @param args
     * @return boolean
     */
    public static boolean isExistNull(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @title isExistNull
     * @description 判断是否存在null
     * @author changpeng
     * @date 2014-11-26 下午9:08:02
     * @param args
     * @return boolean
     */
    public static boolean isNotExistNull(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @title isExistEmpty
     * @description 判断是否存在空，String类型包括""
     * @author changpeng
     * @date 2014-11-26 下午9:07:54
     * @param args
     * @return boolean
     */
    public static boolean isExistEmpty(Object... args) {
        for (Object arg : args) {
            if (arg == null || (arg.getClass().equals(String.class) && StringUtils.isEmpty((String) arg))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @title isExistBlank
     * @description 判断是否存在空，String类型包括""、" "
     * @author changpeng
     * @date 2014-11-26 下午9:07:47
     * @param args
     * @return boolean
     */
    public static boolean isExistBlank(Object... args) {
        for (Object arg : args) {
            if (arg == null || (arg.getClass().equals(String.class) && StringUtils.isBlank((String) arg))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @title isAllNull
     * @description 判断参数是否全部为null
     * @author changpeng
     * @date 2014-11-26 下午9:07:38
     * @param args
     * @return boolean
     */
    public static boolean isAllNull(Object... args) {
        boolean result = true;
        for (Object arg : args) {
            result = result && (null == arg);
        }
        return result;
    }

    /**
     * 
     * @title isAllEmpty
     * @description 判断是否全部为空，String类型包括""
     * @author changpeng
     * @date 2014-11-26 下午9:07:22
     * @param args
     * @return boolean
     */
    public static boolean isAllEmpty(Object... args) {
        boolean result = true;
        for (Object arg : args) {
            result = result && (null == arg || (arg.getClass().equals(String.class) && StringUtils.isEmpty((String) arg)));
        }
        return result;
    }

    /**
     * 
     * @title isAllBlank
     * @description 判断是否全部为空，String类型包括""、" "
     * @author changpeng
     * @date 2014-11-26 下午9:07:09
     * @param args
     * @return boolean
     */
    public static boolean isAllBlank(Object... args) {
        boolean result = true;
        for (Object arg : args) {
            result = result && (null == arg || (arg.getClass().equals(String.class) && StringUtils.isBlank((String) arg)));
        }
        return result;
    }

    /**
     * 
     * @title mapToList
     * @description Map 转 List
     * @author changpeng
     * @date 2014-11-26 下午9:06:36
     * @param <K> 泛型 key
     * @param <V> 泛型 value
     * @param map
     * @return List<V>
     */
    public static <K, V> List<V> mapToList(Map<K, V> map) {
        List<V> list = new ArrayList<V>();
        Iterator<Entry<K, V>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<K, V> entry = iter.next();
            list.add(entry.getValue());
        }
        return list;
    }

    /**
     * 
     * @title genMsg
     * @description 生成提示信息
     * @author changpeng
     * @date 2014-11-26 下午9:06:24
     * @param msgs
     * @return String
     */
    public static String genMsg(String... msgs) {
        if (msgs == null || msgs.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" parameters = [");
        for (String msg : msgs) {
            sb.append(msg).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()).append("]");
        return sb.toString();
    }

    /**
     * 
     * @title getThrowableStack
     * @description 获取异常堆栈信息
     * @author changpeng
     * @date 2014-11-26 下午9:06:15
     * @param e
     * @return String
     */
    public static String getThrowableStack(Throwable e) {
        StringBuilder sb = new StringBuilder(e.toString()).append("\n");
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        for (int index = 0; index < stackTraceElements.length; index++) {
            sb.append("at [" + stackTraceElements[index].getClassName() + ",");
            sb.append(stackTraceElements[index].getFileName() + ",");
            sb.append(stackTraceElements[index].getMethodName() + ",");
            sb.append(stackTraceElements[index].getLineNumber() + "]\n");
        }
        return sb.toString();
    }

    /**
     * 
     * @title getThrowableStack
     * @description 根据length获取异常堆栈信息
     * @author changpeng
     * @date 2014-11-26 下午9:06:08
     * @param e
     * @param length
     * @return String
     */
    public static String getThrowableStack(Throwable e, int length) {
        String errMsg = getThrowableStack(e);
        return ", detailMessage = [\n" + errMsg.substring(0, errMsg.length() > length ? length : errMsg.length()) + "...]";
    }
    
    /**
     * 
     * @title getFormatDateString
     * @description 格式化日期，年-月-日
     * @author changpeng
     * @date 2014-11-26 下午9:05:44
     * @param someDate
     * @param format
     * @return
     * @throws  String
     */
    public static String getFormatDateString(Date someDate, String format) {
        if (someDate == null) {
            return null;
        }
        if(StringUtils.isBlank(format)){
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        String s = simpledateformat.format(someDate).toString();
        return s;
    }
    
    /**
     * 
     * @title getLocalIpAddr
     * @description 返回本地IP
     * @author changpeng
     * @date 2014-11-26 下午9:05:32
     * @return String
     */
    public static String getLocalIpAddr() {
        String ipAddr = "0.0.0.0";
        try {
            InetAddress ip = null;
            // 根据网卡获取IP
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) interfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ip = inetAddress;
                    }
                }
            }
            if (ip == null) {
                ip = InetAddress.getLocalHost();
            }
            ipAddr = ip.getHostAddress();
        } catch (Exception err) {
        }
        return ipAddr;
    }
    
    /**
     * @description 判断字符串是否是合格的数字字符串，空串不是合格的
     * @return boolean
     */
    public static boolean isValidNumeric(String str) {
        return StringUtils.isNotBlank(str) && StringUtils.isNumeric(str);
    }

    private CheckParamUtil() {
    }
}