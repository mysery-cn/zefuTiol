package com.zefu.tiol.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.zefu.tiol.pojo.Item;

/**
 * 实体类与Map转换工具类
 * 
 * @author linch
 * 
 */

public class MapTrunPojo {

	/**
	 * 实体对象转成Map      
	 * 
	 * @param obj
	 *            实体对象    
	 * @return Map
	 */
	public static Map<String, Object> pojo2Map(Object obj) {
		Map<String, Object> map = new HashMap<>();
		if (obj == null) {
			return map;
		}

		Class<? extends Object> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (null != field.get(obj)
						&& (!"serialVersionUID".toUpperCase().equals(field.getName().toUpperCase()))) {
					map.put(getColumName(field.getName()), field.get(obj));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 根据实体类属性名，生成数据库字段名   
	 * 
	 * @param fileName
	 *            属性名    
	 * @return String
	 */
	public static String getColumName(String fileName) {
		// 遍历属性名字符，遇到大写的，在前面加_
		StringBuffer sb = new StringBuffer();
		for (char c : fileName.toCharArray()) {
			if (Character.isUpperCase(c)) {
				sb.append('_');
			}
			sb.append(c);
		}
		return sb.toString().toUpperCase();
	}

	public static void main(String[] args) {
		Item it = new Item();
		it.setCatalogId("1");
		it.setcBizid("yy");
		it.setCompanyId("yy");
		Map<String,Object> map = pojo2Map(it);
		System.out.println(map.get("C_BIZID"));

	}

}
