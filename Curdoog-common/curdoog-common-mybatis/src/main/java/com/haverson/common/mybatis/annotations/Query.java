package com.haverson.common.mybatis.annotations;



import com.haverson.common.mybatis.enums.ConditionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于可配置查询的注解
 * @author
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Query {

    /**
     * 获得列名
     * @return
     */
    String columnName() default "";

    /**
     * 比较条件的类型
     * @return
     */
    ConditionType conditionType() default ConditionType.EQUAL;
}
