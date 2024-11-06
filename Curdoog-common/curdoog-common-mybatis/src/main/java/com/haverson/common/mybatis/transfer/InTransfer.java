package com.haverson.common.mybatis.transfer;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;

import java.util.Collection;
import java.util.List;

public class InTransfer implements IWrapperTransefer {
    @Override
    public <T, R, Children extends AbstractWrapper<T, R, Children>> AbstractWrapper<T, R, Children> transfer(R columnName, Object value, AbstractWrapper<T, R, Children> wrapper) {
        if (value instanceof Collection<?>) {
            return wrapper.in(columnName, (Collection<?>) value);
        } else if (value instanceof List) {
            return wrapper.in(columnName, (List) value);
        }
        else {
            throw new MybatisPlusException("非集合字段不能使用in");
        }
    }
}
