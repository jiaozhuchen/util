/**  
 * @title: ObjectArraySorter.java
 * @package com.yhd.store.service.impl
 * @description: TODO(用一句话描述该文件做什么)
 * @author changpeng
 * @date 2014-12-12 上午9:31:38
 * @version V1.0
 */
package com.cc.libra.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;


/**
 * 传入一个对象数组，一个排序的根据(对象的某个属性)，一个布尔值(是否逆序) ，使用sort方法之后返回一个排序之后的对象数组
 * 
 * @author Administrator
 * 
 */
public class ObjectArraySorterUtil {
	private List<Object> objArray;
	private String sortBy;
	private boolean desc;
	
	public static List sort(List list, String sortBy, boolean desc){
	    if (CollectionUtils.isEmpty(list)) {
	        return list;
	    }
		return new ObjectArraySorterUtil(list, sortBy, desc).sort();
	}

	public ObjectArraySorterUtil(List objArray, String sortBy, boolean desc) {
		this.objArray = objArray;
		this.sortBy = sortBy;
		this.desc = desc;
	}

	// 返回一个排序之后的对象list
	public List sort() {
		Object[] objA = objArray.toArray();
		MyComparator c = new MyComparator(objArray.get(0).getClass().getName(), sortBy, desc);
		Arrays.sort(objA, c);
		List l2 = Arrays.asList(objA);
		return l2;
	}

	class MyComparator implements Comparator {
		private String className;// 要被比较的类
		private String sortBy;// 排序的属性
		private boolean desc = false;// 是否逆序

		/**
		 * 
		 * @param className
		 *            要被比较的类
		 * @param sortBy
		 *            根据哪个属性进行排序
		 * @param desc
		 *            是否逆序
		 */
		public MyComparator(String className, String sortBy, boolean desc) {
			this.className = className;
			this.sortBy = sortBy;
			this.desc = desc;
		}

		public int compare(Object param1, Object param2) {
			try { // 拿到className对应的类
				Class objClass = Class.forName(className);
				// 将参数中2个对象强转为这个类的对象
				Object object1 = objClass.cast(param1);
				Object object2 = objClass.cast(param2);
				// 拿到要被排序的属性
				Field field = objClass.getDeclaredField(sortBy);
				String getMethod = "get";
				String fieldName = field.getName();
				Character fristChar = fieldName.charAt(0);
				Character upperFristChar = fristChar.toUpperCase(fristChar);
				// 获取属性get方法名称
				String newStr = getMethod + upperFristChar + field.getName().substring(1);
				
				Method m = objClass.getMethod(newStr, new Class[] {});
				Object getFieldValue1 = m.invoke(object1, new Object[] {});// 拿到了o1对象的这个属性值
				Object getFieldValue2 = m.invoke(object2, new Object[] {});// 拿到了o2对象的这个属性值
				if(getFieldValue1 == null){
					return 1;
				}
				if(getFieldValue2 == null){
					return -1;
				}
				// 判断这个类的类型
				if (field.getType().getName().equalsIgnoreCase("java.lang.Integer")) { // 如果是数字类,直接返回值之差
					if (!desc)
						return (Integer) getFieldValue1 - (Integer) getFieldValue2 > 0 ? 1 : -1;
					else
						return (Integer) getFieldValue1 - (Integer) getFieldValue2 >= 0 ? -1 : 1;
				} else if (field.getType().getName().equalsIgnoreCase("java.lang.Double")) { // 如果是数字类,直接返回值之差
					if (!desc)
						return (Double) getFieldValue1 - (Double) getFieldValue2 > 0 ? 1 : -1;
					else
						return (Double) getFieldValue1 - (Double) getFieldValue2 >=0 ? -1 : 1;
				} else if (field.getType().getName().equalsIgnoreCase("java.lang.Long")) { // 如果是数字类,直接返回值之差
					if (!desc)
						return (Long) getFieldValue1 - (Long) getFieldValue2 > 0 ? 1 : -1;
					else
						return (Long) getFieldValue1 - (Long) getFieldValue2 >= 0 ? -1 : 1;
				} else if (field.getType().getName().equalsIgnoreCase("java.lang.Float")) { // 如果是数字类,直接返回值之差
					if (!desc)
						return (Float) getFieldValue1 - (Float) getFieldValue2 > 0 ? 1 : -1;
					else
						return (Float) getFieldValue1 - (Float) getFieldValue2 >= 0 ? -1 : 1;
				} else if (field.getType().getName().equalsIgnoreCase("java.util.Date")) {// 如果是日期类,返回日期的毫秒值之差
					Date date1 = (Date) getFieldValue1;
					Date date2 = (Date) getFieldValue2;
					Long l1 = date1.getTime();
					Long l2 = date2.getTime();
					if (!desc)
						return l1 - l2 > 0 ? 1 : -1;
					else
						return l1 - l2 >= 0 ? -1 : 1;
				} else if (field.getType().getName().equalsIgnoreCase("java.lang.String")) {// 如果是字符串，按照字符串的比较规则进行比较
					String str1 = (String) getFieldValue1;
					String str2 = (String) getFieldValue2;
					int index = 0;
					// 首先应该比较字符串的长度,如果第一个比第二个长，则以第一个为准进行循环比较,反之以第二个为准
					int length = (str1.length() > str2.length()) ? str1.length() : str2.length();
					// 从第一个字符开始比较其ASIC码，如果相同继续往下，如果不同直接返回差值
					while (index < length) {
						if (str1.charAt(index) == str2.charAt(index)) {
							index++;
							continue;
						} else {
							try {
								if (!desc)
									return str1.charAt(index) - str2.charAt(index) > 0 ? 1 : -1;
								else
									return str1.charAt(index) - str2.charAt(index) >= 0 ? -1 : 1;
							} catch (NullPointerException e) {// 在这里只可能是短的字符串出现空指针异常
							// 短的排在前面
								if (!desc)
									return -1;
								else
									return 1;
							}
						}
					}
				}
			} catch (Exception e) {
//				e.printStackTrace();
				// 短的排在前面
				if (!desc)
					return -1;
				else
					return 1;
			}
			return 0;
		}
	}
	
	public static List<String> parseLayoutType(String layoutType){
        if(!layoutType.startsWith("grid_")){
            System.err.println("非法的布局信息"+layoutType);
            return null;
        }
        List<String> list = new ArrayList<String>();
        String[] widthes = StringUtils.substringAfter(layoutType, "grid_").split("_");
        switch (widthes.length) {
            case 1:
                list.add("main");
                break;
            case 2:
                //200_770  sub_main
                //770_200 main_sub
                if("200".equals(widthes[0])){
                    list.add("sub");
                    list.add("main");
                }else{
                    list.add("main");
                    list.add("sub");
                }
                break;
            case 3:
                if("560".equals(widthes[0])){
                    list.add("main");
                    list.add("sub");
                    list.add("extra");
                }else if("560".equals(widthes[1])){
                    list.add("sub");
                    list.add("main");
                    list.add("extra");
                }else{
                    list.add("sub");
                    list.add("extra");
                    list.add("main");
                }
                break;
            default:
                break;
        }
        return list;
    }
	
//	public static void main(String [] args){
//		ReportMerchantPublishTimesDto p1 = new ReportMerchantPublishTimesDto();
//		p1.setFirstLevelTypeString("");
//		ReportMerchantPublishTimesDto p2 = new ReportMerchantPublishTimesDto();
//		p2.setFirstLevelTypeString("1223");
//		List<ReportMerchantPublishTimesDto> param = Lists.newArrayList();
//		param.add(p1);
//		param.add(p2);
//		param = ObjectArraySorterUtil.sort(param, "firstLevelTypeString", false);
//		for(ReportMerchantPublishTimesDto p : param){
//			System.out.println( p.getFirstLevelTypeString());
//		}
//	}
}