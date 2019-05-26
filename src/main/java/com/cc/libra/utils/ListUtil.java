package com.cc.libra.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


/**
* @ClassName: ListUtil
* @Description: 集合转换工具类
* @author zhangbei
* @date 2014-4-18 上午10:44:58
*
*/ 
public class ListUtil {
	/**
	 * @Description: 把一个list转换成二维list
	 * @param  list 待转换一维list
	 * @param  subLength 二维list中单个list的长度
	 * @return List<List<T>>
	 * @throws
	 */
	public static <T>List<List<T>> convertTwoDimensionalList(List<T> list, int subLength){
		List<List<T>> lists = new ArrayList<List<T>>();
		int subMax = subLength <= 0 ? 3 : subLength;
		int size = list.size();
		if(size > subMax){
			for(int i=0; i < list.size(); i += subMax){
				lists.add(list.subList(i, (i+subMax) > size ? size : (i+subMax)));
			}
		} else {
			lists.add(list);
		}
		return lists;
	}
	/**
	 * 把一个字符串分段成List
	 * @param str
	 * @param sectionLen
	 * @param separator
	 * @return
	 */
	public static List<String> convertStrToList(String str, int sectionLen, String separator){
		if(str == null || str.trim().length() == 0){
			return new ArrayList<String>();
		}
		String[] arr = str.split(separator);
		if(sectionLen < 1){
			return new ArrayList<String>();
		}
		List<String> strList = Arrays.<String>asList(arr);
		List<String> lists = ListUtil.convertTwoDimensionalStringList(strList, sectionLen, separator);
		
		return lists;
	}
	
	/**
	* @Description: 把一个list转换成二维list
	* @param  list 待转换一维list
	* @param  subLength 二维list中单个list的长度
	* @return List<List<T>>
	* @throws
	 */
	public static List<String> convertTwoDimensionalStringList(List<String> list, int subLength, String separator){
		 List<String> lists = new ArrayList<String>();
	     int subMax = subLength <= 0 ? 3 : subLength;
	     int size = list.size();
	     if(size > subMax){
	      	for(int i=0; i < list.size(); i += subMax){
	      		lists.add(listToString(list.subList(i, (i+subMax) > size ? size : (i+subMax)), separator));
	       	}
	     } else {
	    	 lists.add(listToString(list, separator));
	     }
	     return lists;
	}
	
	/** 
	 * @Description:把list转换为一个用逗号分隔的字符串 
	 */  
	public static String listToString(List list, String separator) {  
	    StringBuilder sb = new StringBuilder();  
	    if (list != null && list.size() > 0) {  
	        for (int i = 0; i < list.size(); i++) {  
	            if (i < list.size() - 1) {  
	                sb.append(list.get(i) + ",");  
	            } else {  
	                sb.append(list.get(i));  
	            }  
	        }  
	    }  
	    return sb.toString();  
	}  
	
	/**
	 * 将以某个分隔符分隔开的String转化成List
	 * @param strs
	 * @param separator
	 * @return
	 */
	public static List<Long> convertStrIntoLongList(String strs ,String separator) {
        if(StringUtils.isNotBlank(strs)) {
            String[] strArray = strs.split(separator);
            List<Long> list = new ArrayList<Long>();
            for(int i = 0; i < strArray.length; i++) {
                String str = strArray[i];
                if(StringUtils.isNotBlank(str) && StringUtils.isNumeric(str)) {
                    list.add(Long.parseLong(str));
                }
            }
            return list;
        }else {
            return new ArrayList<Long>();
        }
    }
    /**
     * 转化List<String>为List<Long>
     * @author liuwentong1
     * @Title listString2listLong
     * @data 2016-6-28  下午4:20:00
     * @param sourseList
     * @return
     */
	public static List<Long> listString2listLong(List<String> sourseList){
        List<Long> targetList = new ArrayList<Long>();
        if(CollectionUtils.isEmpty(sourseList)){
            return targetList;
        }
        for (String str : sourseList) {
            if(!str.matches("^([0-9])+$")){
                continue;
            }
            Long l = Long.parseLong(str);
            targetList.add(l);
        }
        return targetList;
    }
	
	/**
     * 将以某个分隔符分隔开的String转化成List
     * @param strs
     * @param separator
     * @return
     */
    public static List<String> convertStrIntoStringList(String strs ,String separator) {
        if(StringUtils.isNotBlank(strs)) {
            String[] strArray = strs.split(separator);
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < strArray.length; i++) {
                String str = strArray[i];
                if(StringUtils.isNotBlank(str)) {
                    list.add(str);
                }
            }
            return list;
        }else {
            return new ArrayList<String>();
        }
    }
}
