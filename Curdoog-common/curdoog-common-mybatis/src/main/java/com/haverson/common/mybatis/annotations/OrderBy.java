package com.haverson.common.mybatis.annotations;

import com.haverson.common.mybatis.enums.OrderByType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可配置排序注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OrderBy {
    /**
     * 获得列名
     * @return
     */
    String columnName() default "";

    /**
     * 比较条件的类型
     * @return
     */
    OrderByType orderByType() default OrderByType.ORDERBYDESC;
}
