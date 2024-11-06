package com.haverson.common.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haverson.common.mybatis.transfer.TransferFactory;

import java.util.List;

public class XchdServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    public List<T> selectList(Wrapper<T> wrapper) {
        return baseMapper.selectList(wrapper);
    }

    public List<T> selectList(Object param) {
        Wrapper<T> wrapper = TransferFactory.buildWrapper(param);
        return baseMapper.selectList(wrapper);
    }

    public Page<T> selectPage(Page<T> page) {
        return baseMapper.selectPage(page, null);
    }

    public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        return baseMapper.selectPage(page, wrapper);
    }

    public Page<T> selectPage(Page<T> page, Object param) {
        //生成一个 wrapper
        Wrapper<T> wrapper = TransferFactory.buildWrapper(param);
        return baseMapper.selectPage(page, wrapper);
    }

    public T getOne(Object param, boolean throwEx) {
        Wrapper<T> wrapper = TransferFactory.buildWrapper(param);
        return this.getOne(wrapper, throwEx);
    }

    public Long count(Object param) {
        Wrapper<T> wrapper = TransferFactory.buildWrapper(param);
        return baseMapper.selectCount(wrapper);
    }
}
