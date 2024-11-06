package com.haverson.common.mybatis.transfer;


import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

public class LessThanTransfer implements IWrapperTransefer {
    @Override
    public <T, R, Children extends AbstractWrapper<T, R, Children>> AbstractWrapper<T, R, Children> transfer(R columnName, Object value, AbstractWrapper<T, R, Children> wrapper) throws Exception {
        return wrapper.lt(columnName, value);
    }
}
