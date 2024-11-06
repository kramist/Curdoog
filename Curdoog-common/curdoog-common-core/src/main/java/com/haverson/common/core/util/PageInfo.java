package com.haverson.common.core.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 页面显示要素
 */
@Data
public class PageInfo<T> {
    @ApiModelProperty(value = "页码")
    private int pageNum = 1;
    @ApiModelProperty(value = "每页条数")
    private int pageSize = 10;
    @ApiModelProperty(value = "总数")
    private int totalCount = 0;
    @ApiModelProperty(value = "页数")
    private int pageCount = 1;
    @ApiModelProperty(value = "页起始索引")
    private int pageStartIndex = 0;
    @ApiModelProperty(value = "数据")
    private List<T> listData;
}
