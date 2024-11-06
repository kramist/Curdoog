package com.haverson.common.service.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haverson.common.core.util.BeanCopyUtil;
import com.haverson.common.core.util.PageInfo;
import com.haverson.common.core.util.PageInfoUtil;
import com.haverson.common.core.util.RespInfo;
import com.haverson.common.mybatis.service.XchdServiceImpl;
import com.haverson.common.service.api.BaseApi;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

@Component
public class DomainBaseController<M extends BaseMapper<T>, T, V, R> implements BaseApi<V, R> {

    @Autowired
    XchdServiceImpl<M, T> service;
    private Class<V> entityClassV;
    private Class<T> entityClassT;

    @Override
    @ApiOperation(value = "查询列表", notes = "查询列表" )
    public RespInfo<PageInfo<V>> baseSelectPage(Integer pageNum, Integer pageSize, R r) {
        Page<T> page = new Page<>();
        page.setCurrent(Integer.valueOf(pageNum));
        page.setSize(Integer.valueOf(pageSize));
        entityClassV = (Class<V>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        return RespInfo.ok((PageInfo<V>) PageInfoUtil.IPageToPageInfoAndClass(service.selectPage(page, r), entityClassV));
    }

    @Override
    @ApiOperation(value = "新增修改", notes = "新增修改" )
    public RespInfo<V> baseSaveOrUpDate(V v) {
        entityClassT = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        T t = BeanCopyUtil.copy(v, entityClassT);
        service.saveOrUpdate(t);
        entityClassV = (Class<V>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        v = BeanCopyUtil.copy(t, entityClassV);
        return RespInfo.ok(v);
    }

    @Override
    @ApiOperation(value = "根据id查询", notes = "根据id查询" )
    public RespInfo<V> baseGetById(Integer id) {
        T t = service.getById(id);
        entityClassV = (Class<V>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        V v = BeanCopyUtil.copy(t, entityClassV);
        return RespInfo.ok(v);
    }

    @Override
    @ApiOperation(value = "根据删除", notes = "根据删除" )
    public RespInfo baseDeleteById(Integer id) {
        service.removeById(id);
        return RespInfo.ok("操作成功");
    }

}
