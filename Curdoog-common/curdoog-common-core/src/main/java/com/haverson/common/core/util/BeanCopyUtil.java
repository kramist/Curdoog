package com.haverson.common.core.util;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean复制类库
 *
 * @author
 */
public class BeanCopyUtil {
    static Map<String, BeanCopier> beanCopierCache = new ConcurrentHashMap<>();

    /**
     * 单个对象进行复制
     * @param source
     * @param cls
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T copy(Object source, Class<T> cls) {
        if (ObjectUtil.isEmpty(source)) {
            return null;
        }
        T data = null;
        try {
            data = cls.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        BeanCopier copier = getBeanCopier(source.getClass(), cls);
//        copier.copy(source, data, null);
        BeanUtils.copyProperties(source, data);
        return data;
    }

    /**
     * 复制属性
     * @param source
     * @param target
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SneakyThrows
    public static <T> T copyProperties(Object source, T target)  {
        if (ObjectUtil.isEmpty(source) || ObjectUtil.isEmpty(target)) {
            return null;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 进行列表复制
     * @param list
     * @param targetCls
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> copyList(List list, Class<T> targetCls) {
        List<T> dataList = new ArrayList<>();
        if (CollectionUtil.isEmpty(list)) {
            return dataList;
        }
        dataList = copyList(list, dataList, targetCls);

        return dataList;
    }

    /**
     * 进行列表复制
     * @param sourceList
     * @param targetList
     * @param targetCls
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> List<T> copyList(List sourceList, List<T> targetList, Class<T> targetCls) {
        if (CollectionUtil.isEmpty(sourceList)) {
            return targetList;
        }
        BeanCopier copier = getBeanCopier(sourceList.get(0).getClass(), targetCls);
        for (Object source: sourceList) {
            T data = copy(source, targetCls);
            targetList.add(data);
        }

        return targetList;
    }

    /**
     * 通过 tClass 生成示例并且将 source 对应的字段 copy
     * @param source 源对象
     * @param tClass 期望对象类型
     * @param <S> 源对象
     * @param <T> 期望对象
     * @return 期望对象实例
     */
    public static <S,T> T copyProperties(S source, Class<T> tClass) {
        return copy(source, tClass);
    }

    static BeanCopier getBeanCopier(Class<?> sourceCls, Class<?> targetCls) {
        String beanName = sourceCls.getName() + "-" + targetCls.getName();
        BeanCopier copier = null;
        if (beanCopierCache.containsKey(beanName)) {
            copier = beanCopierCache.get(beanName);
        } else {
            copier = BeanCopier.create(sourceCls, targetCls, false);
            beanCopierCache.put(beanName, copier);
        }
        return copier;
    }
}
