package com.protops.gateway.util.excel;

import java.util.List;


public interface Refect {
	/**
	 * 获取一个类所有属性的注释值
	 * @param class1
	 * @return
	 */
	public String[] getFieldAnnotation(Class<?> c);

	/**
	 * 获取一个对象的取值，装入list
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFieldValue(Object obj);

}
