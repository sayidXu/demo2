package com.xutt.sky.portal.util;

import java.lang.reflect.Method;

/**
 * 反射获取set和get方法的工具类
 * 
 * @author wondersgroup
 *
 */
public abstract class AccessorUtils {

	/**
	 * 获取对象字段的set方法
	 * 
	 * @param obj
	 *            实体对象
	 * @param field
	 *            实体对象的属性字段名
	 * @return 实体属性的set方法对象
	 * @throws NoSuchMethodException
	 *             方法不存在的时候抛出该异常
	 */
	public static Method findSetter(Object obj, String field) throws NoSuchMethodException {
		for (Method m : obj.getClass().getMethods()) {
			if (m.getName().equals(findSetterName(field))) {
				return m;
			}
		}
		return null;
	}

	/**
	 * 获取对象字段的get方法
	 * 
	 * @param obj
	 *            实体对象
	 * @param field
	 *            实体对象的属性字段名
	 * @return 实体属性的get方法对象
	 * @throws NoSuchMethodException
	 *             方法不存在的时候抛出该异常
	 */
	public static Method findGetter(Object obj, String field) throws NoSuchMethodException {
		// data.getClass().getDeclaredMethod()
		for (Method m : obj.getClass().getMethods()) {
			if (m.getName().equals(findGetterName(field))) {
				return m;
			}
		}
		return null;
	}

	/**
	 * 根据字段名生成对应的get方法名
	 * 
	 * @param fieldName
	 *            字段名
	 * @return get方法名
	 */
	private static String findGetterName(String fieldName) {
		return "get" + makeFirstUpper(fieldName);

	}

	/**
	 * 根据字段名生成对应的set方法名
	 * 
	 * @param fieldName
	 *            字段名
	 * @return set方法名
	 */
	private static String findSetterName(String fieldName) {
		return "set" + makeFirstUpper(fieldName);

	}

	/**
	 * 将字符串的首字母变大写
	 * 
	 * @param str
	 *            字符串
	 * @return 首字母大写的字符串
	 */
	private static String makeFirstUpper(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}
}
