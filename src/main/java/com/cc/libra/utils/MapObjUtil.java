package com.cc.libra.utils;


import org.apache.commons.lang.StringUtils;

/**
 * <p>将Map<key, Object>的value转成对应类型的对象 </p>
 * @author chenchen6
 * @date 2017年2月21日
 */
public class MapObjUtil {
	public static Long objToLong(Object obj, long defaultLong) {
		Long val = objToLong(obj);
		if (null == val) {
			return defaultLong;
		}
		return val;
	}
    public static Long objToLong(Object obj){
        if(obj == null) {
            return null;
        }
        if(obj instanceof Long) {
            return (Long) obj;
        }
        if(obj instanceof String) {
            if(StringUtils.isNumeric((String) obj)) {
                return Long.parseLong((String) obj);
            }
            if(StringUtils.isNumeric(((String) obj).trim())) {
                return Long.parseLong(((String) obj).trim());
            }
        }
        if(obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        return null;
    }
    
    public static Integer objToInteger(Object obj) {
        if(obj == null) {
            return null;
        }
        if(obj instanceof Integer) {
            return (Integer) obj;
        }
        if(obj instanceof String) {
            if(StringUtils.isNumeric((String) obj)) {
                return Integer.parseInt((String) obj);
            }
            if(StringUtils.isNumeric(((String) obj).trim())) {
                return Integer.parseInt(((String) obj).trim());
            }
        }
        if(obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return null;
    }
    
    public static Double objToDouble(Object obj) {
        if(obj == null){
            return null;
        }
        if(obj instanceof Double) {
            return (Double) obj;
        }
        if(obj instanceof String) {
            if(StringUtils.isNumeric((String) obj)) {
                return Double.parseDouble((String) obj);
            }
            if(StringUtils.isNumeric(((String) obj).trim())) {
                return Double.parseDouble(((String) obj).trim());
            }
        }
        if(obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return null;
    }
    
    public static Float objToFloat(Object obj) {
        if(obj == null) {
            return null;
        }
        if(obj instanceof Float) {
            return (Float) obj;
        }
        if(obj instanceof String) {
            if(StringUtils.isNumeric((String) obj)) {
                return Float.parseFloat((String) obj);
            }
            if(StringUtils.isNumeric(((String) obj).trim())) {
                return Float.parseFloat(((String) obj).trim());
            }
        }
        if(obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        return null;
    }
    
//    public static void main(String [] args) {
//        Map<String, Object> map = Maps.newHashMap();
//        map.put("aa", "1");
//        map.put("bb", "2 ");
//        map.put("cc", 3.0);
//        map.put("dd", "4");
//        List<String> stringList = Lists.newArrayList();
//        stringList.add("ee");
//        map.put("ee", stringList);
//        Long aa = objToLong(map.get("aa"));
//        Integer bb = objToInteger(map.get("bb"));
//        Double cc = objToDouble(map.get("cc"));
//        Float dd = objToFloat(map.get("dd"));
//        List ee = (List) map.get("ee");
//        System.out.println(aa);
//        System.out.println(bb);
//        System.out.println(cc);
//        System.out.println(dd);
//        System.out.println(ee);
//        Number n = 11111111111111L;
//        Integer i = n.intValue();
//        System.out.println(i);
//    }
}
