package com.haverson.common.mybatis.transfer;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

public class GreaterThanTransfer implements IWrapperTransefer {
    @Override
    public <T, R, Children extends AbstractWrapper<T, R, Children>> AbstractWrapper<T, R, Children> transfer(R columnName, Object value, AbstractWrapper<T, R, Children> wrapper) {
        return wrapper.gt(columnName, value);
    }
}
