package com.haverson.common.service.controller;

import com.haverson.common.core.util.PageInfo;
import com.haverson.common.core.util.RespInfo;
import com.haverson.common.service.api.BaseApi;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
public class ApplicationBaseController<V, R> {
    private static final String DEFAULT_PAGE_NUM_STR = "1";
    private static final String DEFAULT_PAGE_SIZE_STR = "20";
    @Autowired
    BaseApi<V, R> baseApi;


    @ApiOperation(value = "基础分页查询")
    @RequestMapping(value = "/baseSelectPage", method = RequestMethod.POST)
    public RespInfo<PageInfo<V>> baseSelectPage(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUM_STR, value = "pageNum") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR, value = "pageSize") Integer pageSize,
                                                @RequestBody R r) {
        PageInfo<V> vPageInfo = baseApi.baseSelectPage(pageNum, pageSize, r).getData();
        return RespInfo.ok(vPageInfo);
    }

    @ApiOperation(value = "基础新增或修改")
    @PostMapping("/baseSaveOrUpDate")
    public RespInfo<V> baseSaveOrUpDate(@RequestBody V v) {
        v = baseApi.baseSaveOrUpDate(v).getData();
        return RespInfo.ok(v);
    }

    @ApiOperation(value = "基础根据id获取详情")
    @PostMapping("/baseGetById")
    public RespInfo<V> baseGetById(@RequestParam(value = "id") Integer id) {
        V v = baseApi.baseGetById(id).getData();
        return RespInfo.ok(v);
    }

    @ApiOperation(value = "基础根据id删除")
    @PostMapping("/baseDeleteById")
    public RespInfo baseDeleteById(@RequestParam(value = "id") Integer id) {
        baseApi.baseDeleteById(id);
        return RespInfo.ok("操作成功");
    }
}
