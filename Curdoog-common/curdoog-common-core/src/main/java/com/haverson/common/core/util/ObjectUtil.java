package com.haverson.common.core.util;

import org.springframework.lang.Nullable;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Optional;

/**
 * 对象工具类
 *
 * @author jiangwei
 */
public class ObjectUtil extends org.springframework.util.ObjectUtils {

    /**
     * 判断元素不为空
     *
     * @param obj object
     * @return boolean
     */
    public static boolean isNotEmpty(@Nullable Object obj) {
        return !ObjectUtil.isEmpty(obj);
    }

    /**
     * 判断元素不为空,但是不判断数组是否为空,政务云空数组也加密返回
     *
     * @param obj object
     * @return boolean
     */
    public static boolean isNotEmptyExcludeCol(@Nullable Object obj) {
        return !isEmptyExcludeCol(obj);
    }

    /**
     * 判断元素为空,但是不判定数组
     *
     * @param obj object
     * @return boolean
     */
    private static boolean isEmptyExcludeCol(@Nullable Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Optional) {
            return !((Optional) obj).isPresent();
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else {
            return obj instanceof Map ? ((Map) obj).isEmpty() : false;
        }
    }
}
