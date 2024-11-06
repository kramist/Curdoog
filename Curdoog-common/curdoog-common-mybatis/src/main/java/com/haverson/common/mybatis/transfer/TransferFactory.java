package com.haverson.common.mybatis.transfer;


import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.haverson.common.mybatis.annotations.OrderBy;
import com.haverson.common.mybatis.annotations.Query;
import com.haverson.common.mybatis.enums.ConditionType;
import com.haverson.common.mybatis.enums.OrderByType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 转换器工厂
 *
 * @author
 */
@Slf4j
public class TransferFactory {


    /**
     * 工厂模式  生成一个转换器
     * @param conditionType
     * @return
     */
    public static IWrapperTransefer create(ConditionType conditionType) {
        IWrapperTransefer transefer = null;
        switch (conditionType) {
            case IN:
                transefer = new InTransfer();
                break;
            case LESSEQUAL:
                transefer = new LessEqualTransfer();
                break;
            case LESSTHAN:
                transefer = new LessThanTransfer();
                break;
            case GREATERTHAN:
                transefer = new GreaterThanTransfer();
                break;
            case GREATEREQUAL:
                transefer = new GreaterEqualTransfer();
                break;
            case ALLLIKE:
                transefer = new AllLikeTransfer();
                break;
            case LEFTLIKE:
                transefer = new LeftLikeTransfer();
                break;
            default:
                transefer = new EqualTransfer();
                break;
        }
        return transefer;
    }

    /**
     * @param orderByType
     * @return
     */
    public static IWrapperTransefer createOrder(OrderByType orderByType) {
        IWrapperTransefer transefer = null;
        switch (orderByType) {
            case ORDERBYDESC:
                transefer = new OrderByDescTransfer();
                break;
            case ORDERBYASC:
                transefer = new OrderByAscTransfer();
                break;
            default:
                transefer = new OrderByDescTransfer();
                break;
        }
        return transefer;
    }

    /**
     * 根据注解生成wrapper
     *
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T, R, Children extends AbstractWrapper<T, R, Children>> AbstractWrapper<T, R, Children> buildWrapper(Object obj) {
        Map<String, Query> anotationMap = new HashMap<String, Query>();
        Map<String, OrderBy> anotationOrderByMap = new HashMap<String, OrderBy>();
        Map<String, Object> valueMap = new HashMap<String, Object>();
        AbstractWrapper<T, R, Children> wrapper = new QueryWrapper();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            //驼峰转下划线，以便对应数据库字段
            String columnName = StringUtils.camelToUnderline(f.getName());
            Query query = f.getAnnotation(Query.class);
            OrderBy orderBy = f.getAnnotation(OrderBy.class);
            Object value = null;
            try {
                f.setAccessible(true);
                value = f.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (ObjectUtils.isEmpty(orderBy) && ObjectUtils.isEmpty(value)) {
                continue;
            }
            if (ObjectUtils.isNotEmpty(query) && StringUtils.isNotBlank(query.columnName())) {
                columnName = query.columnName();
            }
            if (ObjectUtils.isNotEmpty(orderBy) && StringUtils.isNotBlank(orderBy.columnName())) {
                columnName = orderBy.columnName();
            }
            valueMap.put(columnName, value);
            if (ObjectUtils.isNotEmpty(query)&& ObjectUtils.isNotEmpty(value)) {
                anotationMap.put(columnName, query);
            }
            if (ObjectUtils.isNotEmpty(orderBy)) {
                anotationOrderByMap.put(columnName, orderBy);
            }
        }
        for (Map.Entry<String, Query> q : anotationMap.entrySet()) {
            String columnName = q.getKey();
            Query query = q.getValue();
            if (!valueMap.containsKey(columnName)) {
                continue;
            }
            ConditionType conditionType = ConditionType.EQUAL;
            if (query != null) {
                conditionType = query.conditionType();
            }
            IWrapperTransefer transefer = create(conditionType);
            try {
                wrapper = transefer.transfer((R) columnName, valueMap.get(columnName), wrapper);
            } catch (Exception e) {
//                e.printStackTrace();
                log.error(e.getMessage(), e);
            }

        }
        for (Map.Entry<String, OrderBy> q : anotationOrderByMap.entrySet()) {
            String columnName = q.getKey();
            OrderBy orderBy = q.getValue();
            if (!valueMap.containsKey(columnName)) {
                continue;
            }
            OrderByType orderByType = OrderByType.ORDERBYDESC;
            if (orderBy != null) {
                orderByType = orderBy.orderByType();
            }
            IWrapperTransefer transefer = createOrder(orderByType);
            try {
                wrapper = transefer.transfer((R) columnName, valueMap.get(columnName), wrapper);
            } catch (Exception e) {
//                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
        return wrapper;
    }
}
