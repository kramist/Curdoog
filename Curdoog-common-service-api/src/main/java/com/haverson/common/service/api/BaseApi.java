package com.haverson.common.service.api;


import com.haverson.common.core.util.PageInfo;
import com.haverson.common.core.util.RespInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

public interface BaseApi<V, R> {
    String DEFAULT_PAGE_NUM_STR = "1";
    String DEFAULT_PAGE_SIZE_STR = "20";


    @ApiOperation(value = "基础分页查询")
    @RequestMapping(value ="/baseSelectPage", method = RequestMethod.POST)
    RespInfo<PageInfo<V>> baseSelectPage(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUM_STR ,value = "pageNum") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE_STR,value = "pageSize") Integer pageSize,
                                         @RequestBody R r);

    @ApiOperation(value = "基础新增或修改")
    @PostMapping("/baseSaveOrUpDate")
    RespInfo<V> baseSaveOrUpDate(@RequestBody V v);

    @ApiOperation(value = "基础根据id获取详情")
    @PostMapping("/baseGetById")
    RespInfo<V> baseGetById(@RequestParam(value ="id" ) Integer id);

    @ApiOperation(value = "基础根据id删除")
    @PostMapping("/baseDeleteById")
    RespInfo baseDeleteById(@RequestParam(value ="id" ) Integer id);
}

