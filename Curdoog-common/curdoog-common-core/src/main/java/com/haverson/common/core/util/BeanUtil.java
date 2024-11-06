
package com.haverson.common.core.util;


import org.springframework.beans.BeansException;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 实体工具类
 *
 * @author jiangwei
 */
public class BeanUtil extends org.springframework.beans.BeanUtils {

	/**
	 * 实例化对象
	 * @param clazz 类
	 * @param <T> 泛型标记
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> clazz) {
		return (T) instantiateClass(clazz);
	}

	/**
	 * 实例化对象
	 * @param clazzStr 类名
	 * @param <T> 泛型标记
	 * @return 对象
	 */
	public static <T> T newInstance(String clazzStr) {
		try {
			Class<?> clazz = Class.forName(clazzStr);
			return newInstance(clazz);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取Bean的属性
	 * @param bean bean
	 * @param propertyName 属性名
	 * @return 属性值
	 */
	public static Object getProperty(Object bean, String propertyName) {
		Assert.notNull(bean, "bean Could not null");
		return BeanMap.create(bean).get(propertyName);
	}

	/**
	 * 设置Bean属性
	 * @param bean bean
	 * @param propertyName 属性名
	 * @param value 属性值
	 */
	public static void setProperty(Object bean, String propertyName, Object value) {
		Assert.notNull(bean, "bean Could not null");
		BeanMap.create(bean).put(propertyName, value);
	}

	/**
	 * 将对象装成map形式
	 * @param bean 源对象
	 * @return {Map}
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object bean) {
		return BeanMap.create(bean);
	}

	/**
	 * 将map 转为 bean
	 * @param beanMap map
	 * @param valueType 对象类型
	 * @param <T> 泛型标记
	 * @return {T}
	 */
	public static <T> T toBean(Map<String, Object> beanMap, Class<T> valueType) {
		T bean = BeanUtil.newInstance(valueType);
		BeanMap.create(bean).putAll(beanMap);
		return bean;
	}

	/**
	 * 给一个Bean添加字段
	 * @param superBean 父级Bean
	 * @param props 新增属性
	 * @return  {Object}
	 */
	public static Object generator(Object superBean, BeanProperty... props) {
		Class<?> superclass = superBean.getClass();
		Object genBean = generator(superclass, props);
		BeanCopyUtil.copyProperties(superBean, genBean);
		return genBean;
	}

	/**
	 * 给一个class添加字段
	 * @param superclass 父级
	 * @param props 新增属性
	 * @return {Object}
	 */
	public static Object generator(Class<?> superclass, BeanProperty... props) {
		BeanGenerator generator = new BeanGenerator();
		generator.setSuperclass(superclass);
		generator.setUseCache(true);
		for (BeanProperty prop : props) {
			generator.addProperty(prop.getName(), prop.getType());
		}
		return generator.create();
	}

	/**
	 * 获取 Bean 的所有 get方法
	 * @param type 类
	 * @return PropertyDescriptor数组
	 */
	public static PropertyDescriptor[] getBeanGetters(Class type) {
		return getPropertiesHelper(type, true, false);
	}

	/**
	 * 获取 Bean 的所有 set方法
	 * @param type 类
	 * @return PropertyDescriptor数组
	 */
	public static PropertyDescriptor[] getBeanSetters(Class type) {
		return getPropertiesHelper(type, false, true);
	}

	private static PropertyDescriptor[] getPropertiesHelper(Class type, boolean read, boolean write) {
		try {
			PropertyDescriptor[] all = BeanUtil.getPropertyDescriptors(type);
			if (read && write) {
				return all;
			} else {
				List<PropertyDescriptor> properties = new ArrayList<>(all.length);
				for (PropertyDescriptor pd : all) {
					if (read && pd.getReadMethod() != null) {
						properties.add(pd);
					} else if (write && pd.getWriteMethod() != null) {
						properties.add(pd);
					}
				}
				return properties.toArray(new PropertyDescriptor[0]);
			}
		} catch (BeansException ex) {
			throw new CodeGenerationException(ex);
		}
	}

}
